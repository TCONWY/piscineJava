package edu.school21.app;
import edu.school21.app.classes.Car;
import edu.school21.app.classes.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
	public static void main(String[] args) {
		List<Object> objects = new ArrayList<>();
		objects.add(new Car());
		objects.add(new User());
		Scanner scanner = new Scanner(System.in);
		System.out.println("classes:");
		for(Object o : objects)
			System.out.println(" - " +o.getClass().getSimpleName());
		System.out.println("---------------------");
		System.out.println("Enter class name:");
		System.out.println("---------------------");
		Object object = getObject(scanner, objects);
		printDataClass(object);
		System.out.println("---------------------");
		System.out.println("Let’s create an object.");
		Object newObject = createObject(object, scanner);
		System.out.println("---------------------");
		changeObject(newObject, scanner);
		System.out.println("---------------------");
		callMethods(newObject, scanner);
	}

	public static void callMethods(Object object, Scanner scanner) {
		System.out.println("Enter name of the method for call:");
		String line = scanner.nextLine();
		String str = null;
		try {
			for (Method method : object.getClass().getDeclaredMethods()) {
				str = method.getName() + '(' + getParameters(method) + ')';
				if (str.equals(line)) {
					Field[] fields = object.getClass().getDeclaredFields();
					Class<?>[] classes = method.getParameterTypes();
					Object[] objects = new Object[classes.length];
					int index = 0;
					while (classes.length > index) {
						System.out.print("Enter " + classes[index].getSimpleName() + " value:\n->");
						line = scanner.nextLine();
						objects[index] = classes[index].getConstructor(String.class).newInstance(line);
						++index;
					}
					Object invoke = method.invoke(object, objects);
					if (invoke != null) {
						System.out.println("Method returned:");
						System.out.println(invoke.toString());
					}
				}
			}
		} catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
			System.err.println("illegal value");;
		}
	}

	public static void printDataClass(Object object){
		System.out.println("fields:");
		for (Field field: object.getClass().getDeclaredFields()) {
			System.out.println("	" + field.getType().getSimpleName()+ " " + field.getName());
		}
		System.out.println("methods:");
		for (Method method: object.getClass().getDeclaredMethods()) {
			System.out.println("	" + method.getReturnType().getSimpleName() + " " + method.getName() +
					'(' + getParameters(method) + ')');
		}
	}

	public static void changeObject(Object object, Scanner scanner) {
		System.out.print("Enter name of the field for changing:\n-> ");
		String line;
		line = scanner.nextLine();
		Field field = null;
		try {
			field = object.getClass().getDeclaredField(line);
			System.out.print("Enter" + field.getType().getSimpleName() + "value:\n-> ");
			line = scanner.nextLine();
			field.setAccessible(true);
			field.set(object, field.getType().getConstructor(String.class).newInstance(line));
			field.setAccessible(false);
			System.out.print("Object updated: " + object.toString() + "\n");
		} catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
			System.err.println("field not found");
			System.exit(-1);
		}
	}

	public static Object createObject(Object object, Scanner scanner) {
		Field[]     fields = object.getClass().getDeclaredFields();
		Class<?>[]  classes = new Class[fields.length];
		Object[]    objects = new Object[fields.length];
		String      args = null;
		int         index = 0;
		try {
			for (Field field : fields) {
				classes[index] = field.getType();
				System.out.printf("%s:\n-> ", field.getName());
				args = scanner.nextLine();
				objects[index] = classes[index].getConstructor(String.class).newInstance(args);
				++index;
			}
			Object result = object.getClass().getConstructor(classes).newInstance(objects);
			System.out.printf("Object created: %s\n", result.toString());
			return result;
		} catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
			System.err.println("illegal field");
			System.exit(-1);
		}
		return null;
	}

	public static String getParameters(Method method){
		String str = null;
		Class<?>[] parameters = method.getParameterTypes();
		for (int i = 0; i < parameters.length; i++){
			if(str == null)
				str = parameters[i].getSimpleName();
			else
				str = str + ", " + parameters[i].getSimpleName();
		}
		if (str == null)
			str = "";
		return str;
	}

	public static Object getObject(Scanner scanner, List<Object> objects) {
		String className;
		System.out.print("->");
		className = scanner.nextLine();
		for (Object o : objects) {
			if (o.getClass().getSimpleName().equals(className)) {
				return o;
			}
		}
		System.err.println("Class not found");
		System.exit(-1);
		return (null);
	}
}
