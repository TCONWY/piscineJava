package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbclmpl implements UserRepository{

    final   String QUERY_TEMPLATE = "SELECT (SELECT COUNT(id) FROM chat.user) as sum, chat.user.id as user_id, chat.user.login as user_login, chat.user.password as user_pas, chat.room.id as room_id, chat.room.name as room_name, chat.room.owner as room_owner, a1.id as room_user_id, a1.name as room_user_name, a1.owner as room_user_owner\n" +
        "    FROM chat.user\n" +
        "             LEFT JOIN chat.room ON chat.user.id = chat.room.owner\n" +
        "             LEFT JOIN chat.message ON chat.user.id = chat.message.author\n" +
        "             LEFT JOIN chat.room a1 ON chat.message.room = a1.id\n" +
        "    ORDER BY chat.user.id, chat.room.id";

    private final Connection connection;
    private final String COMAND = "SELECT * FROM chat.user WHERE id=?";

    public UserRepositoryJdbclmpl(Connection dataSource) {
        this.connection = dataSource;
    }

    @Override
    public Optional<User> findById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(COMAND);
        statement.setLong(1, id);
        UserRepositoryJdbclmpl user = new UserRepositoryJdbclmpl(connection);
        ResultSet result = statement.executeQuery();
        User user1 = null;
        if(result.next()) {
            user1 = new User(result.getLong("id"),
                    result.getString("login"),
                    result.getString("password"),
                    new ArrayList<>(), new ArrayList<>());
        }
        statement.close();
        result.close();
        return Optional.ofNullable(user1);
    }

    @Override
    public List<User> findAll(int page, int size) {
        PreparedStatement pst ;
        ResultSet rs;
        HashMap<Integer, User> list = new HashMap<>();
        try {
            pst = connection.prepareStatement(QUERY_TEMPLATE);
            rs = pst.executeQuery();
            int pages = ((page * size) - size) + 1;
            long id = 1, pas_id = -1, cur_id;
            while (id < pages && rs.next())
            {
                if (pas_id != (cur_id = rs.getLong("user_id")))
                {
                    id++;
                    pas_id = cur_id;
                }
            }
            pages += size;
            int sum = rs.getInt(1);
            if (id > sum){
                return null;
            }
            while (id < pages && rs.next()) {
                int i = rs.getInt(1);
                if (list.get(i) != null)
                {
                    int ids = rs.getInt("room_id");
                    boolean finded = false;
                    for (Chatroom room : list.get(i).getCreateRooms())
                    {
                        if (room.getId() == ids) {
                            finded = true;
                            break;
                        }
                    }
                    if (!finded) {
                        list.get(i).getCreateRooms().add(createChatroom(rs, 1));
                    }
                    finded = false;
                    ids = rs.getInt("room_user_id");
                    for (Chatroom room : list.get(i).getRooms())
                    {
                        if (room.getId() == ids) {
                            finded = true;
                            break;
                        }
                    }
                    if (!finded) {
                        list.get(i).getRooms().add(createChatroom(rs, 2));
                    }
                }
                else {
                    list.put(i, getNewUser(rs));
                    id++;
                }
            }
            return new ArrayList<>(list.values());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
        return null;
    }

    private Chatroom createChatroom(ResultSet rs, int action)
    {
        Chatroom chat;
        if (action == 1)
        {
            try {
                return new Chatroom(rs.getLong("room_id"), rs.getString("room_name"), null, null);
            }
            catch (Exception ignored)
            {
                return null;
            }
        }
        else if (action == 2)
        {
            try {
                return new Chatroom(rs.getLong("room_user_id"), rs.getString("room_user_name"), null, null);
            }
            catch (Exception ignored)
            {
                return null;
            }
        }
        return null;
    }

    private User getNewUser(ResultSet rs)
    {
        try {
            ArrayList<Chatroom> owner = new ArrayList<>(), socializer = new ArrayList<>();
            owner.add(createChatroom(rs, 1));
            socializer.add(createChatroom(rs, 2));
            return new User(rs.getLong("user_id"), rs.getString("user_login"), rs.getString("user_pas"), owner, socializer);
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
            System.exit((-1));
        }
        return null;
    }
}