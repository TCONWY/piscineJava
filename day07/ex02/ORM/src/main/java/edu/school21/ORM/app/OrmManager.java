package edu.school21.ORM.app;

import edu.school21.ORM.annotations.OrmColumn;
import edu.school21.ORM.annotations.OrmColumnId;
import edu.school21.ORM.annotations.OrmEntity;

import javax.sql.DataSource;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrmManager {
	private Connection connection;
	private String packageName;

	public OrmManager(String packageName, DataSource dataSource){
		this.packageName = packageName;
		try {
			this.connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void init() {
		String scannedPath = packageName.replace('.', '/');
		URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
		if (scannedUrl == null) { System.err.println("Not found classes!"); }
		File scannedDir = new File(scannedUrl.getFile());
		try {
			PreparedStatement statement;
			for (File file : scannedDir.listFiles()) {
				String className = file.getName().replace(".class", "");
				Class<?> aClass = Class.forName(packageName + className);

				if (aClass.isAnnotationPresent(OrmEntity.class)) {
					OrmEntity entity = aClass.getAnnotation(OrmEntity.class);
					String sql_drop_table = "DROP TABLE " + entity.table() + " IF EXISTS;";
					statement = connection.prepareStatement(sql_drop_table);
					statement.execute();
					System.out.println(sql_drop_table);
					String sql_create_table = "CREATE TABLE " + entity.table() + " (\n";
					for (Field f : aClass.getDeclaredFields()) {
						if (f.isAnnotationPresent(OrmColumnId.class)) {
							String typeName = f.getType().getSimpleName();
							if (typeName.equals("Long")) {
								sql_create_table += "\t" + f.getName() + " BIGINT PRIMARY KEY,\n";
							} else {
								throw new IllegalArgumentException(aClass.getName() + " PRIMARY KEY field \"" + f.getName() + "\" must be a Long type!");
							}
						} else if (f.isAnnotationPresent(OrmColumn.class)) {
							OrmColumn column = f.getAnnotation(OrmColumn.class);
							sql_create_table += "\t" + column.name() + " " +
									sqlType(f.getType().getSimpleName(), 10, aClass.getName() + "." + f.getName()) + ",\n";
						}
					}
					sql_create_table += ");";
					statement = connection.prepareStatement(sql_create_table);
					statement.execute();
					System.out.println(sql_create_table);
				}
			}
		} catch (ClassNotFoundException | IllegalArgumentException | SQLException e) {
			System.err.println("error in it");
			System.exit(-1);
		}
	}

	private String sqlType(String typeName, int length, String classWithField) {
		if (typeName.equals("Integer")) { return "INT"; }
		else if (typeName.equals("Long")) {	return "BIGINT"; }
		else if (typeName.equals("String")) { return "VARCHAR(" + length + ")"; }
		else if (typeName.equals("Double")) { return "DOUBLE"; }
		else if (typeName.equals("Boolean")) { return "BOOLEAN"; }
		else {
			throw new IllegalArgumentException(classWithField + " contains not valid type \"" + typeName+"\"!");
		}
	}

	public void save(Object obj) throws NoSuchMethodException {
		Class<?> c = obj.getClass();
		if (c.isAnnotationPresent(OrmEntity.class)) {
			try {
				OrmEntity entity = c.getAnnotation(OrmEntity.class);
				String attributes = "(";
				String entities = "(";
				Field[] fields = c.getDeclaredFields();
				for (Field f : fields) {
					f.setAccessible(true);
					if (f.isAnnotationPresent(OrmColumnId.class)) {
						entities += f.get(obj).toString() + ", ";
						attributes += f.getName()  + ", ";
					}
					else if (f.isAnnotationPresent(OrmColumn.class)){
						OrmColumn column = f.getAnnotation(OrmColumn.class);
						String attr = column.name();
						attributes += attr + ", ";
						Object value = f.get(obj);
						if (f.getType() == Integer.class) {
							entities += value + ", ";
						} else {
							entities += "'" + value + "', ";
						}
					}
				}
				attributes = attributes.substring(0, attributes.lastIndexOf(","));
				attributes += ")";
				entities = entities.substring(0, entities.lastIndexOf(","));
				entities += ")";
				System.out.println(attributes);
				System.out.println(entities);
				String sql = "INSERT INTO " + entity.table() + " " + attributes + " VALUES " + entities;
				System.out.println(sql);
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.execute();
			} catch (SQLException | IllegalAccessException e) {
				System.err.println("error save");
				System.exit(-1);
			}
		}
	}
 
	public void update(Object obj) {
		Class<?> c = obj.getClass();
		if (c.isAnnotationPresent(OrmEntity.class)) {
			try {
				OrmEntity entity = c.getAnnotation(OrmEntity.class);
				String update = "UPDATE " + entity.table() + " SET ";
				String id = "1";
				for (Field f : c.getDeclaredFields()) {
					f.setAccessible(true);
					if (f.isAnnotationPresent(OrmColumnId.class)) {
						id = f.get(obj).toString();
					}
					else if (f.isAnnotationPresent(OrmColumn.class)){
						OrmColumn column = f.getAnnotation(OrmColumn.class);
						update += column.name() + " = ";
						if (f.getType().getSimpleName().equals("String")) {
							update += "'" + f.get(obj) + "', ";
						} else {
							update += f.get(obj) + ", ";
						}
					}
				}
				update = update.substring(0, update.length() - 2);
				update += " WHERE id = " + id + ";";
				System.out.println(update);
				PreparedStatement statement = connection.prepareStatement(update);
				statement.execute();
				System.out.println(update);
			} catch (IllegalAccessException | SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public <T> T findById(Long id, Class<T> aClass) {
		if (aClass.isAnnotationPresent(OrmEntity.class)){
			try {
				OrmEntity entity = aClass.getAnnotation(OrmEntity.class);
				String find = "SELECT * FROM " + entity.table() + " WHERE id = " + id + ";";
				PreparedStatement statement = connection.prepareStatement(find);
				statement.execute();
				ResultSet resultSet = statement.getResultSet();
				if (resultSet.next()){
					T obj = aClass.newInstance();
					for (Field f : aClass.getDeclaredFields()){
						f.setAccessible(true);
						if(f.isAnnotationPresent(OrmColumn.class)){
							OrmColumn column = f.getAnnotation(OrmColumn.class);
							f.set(obj, resultSet.getObject(column.name()));
						}
						else if(f.isAnnotationPresent(OrmColumnId.class)) {
							f.set(obj, resultSet.getLong("id"));
						}
					}
					return obj;
				}
				return null;
			} catch (IllegalAccessException | SQLException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				System.out.println("Not found constructor without parameters!");
				return null;
			}
		}
		return null;
	}
}
