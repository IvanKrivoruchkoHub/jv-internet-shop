package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.DataProcessingExeption;
import mate.academy.internetshop.lib.anotations.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;

@Dao
public class UserDaoJdbcImpl extends AbcstractDao<User> implements UserDao {
    private static final String DB_NAME = "internetShop_db";

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Optional<User> findByLogin(String login) throws DataProcessingExeption {
        String query = String.format("select users.user_id, users.name, "
                + "users.surname, users.login, users.password,\n"
                + "roles.role_name, roles.role_id\n"
                + "from %1$s.users\n"
                + "join %1$s.users_roles using(user_id)\n"
                + "join %1$s.roles using(role_id)\n"
                + "where users.login = ?", DB_NAME);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            return getUser(preparedStatement);
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't get user with login = " + login, e);
        }
    }

    private Optional<User> getUser(PreparedStatement preparedStatement)
            throws DataProcessingExeption {
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
            User user = null;
            while (resultSet.next()) {
                if (user == null) {
                    user = new User();
                    user.setId(resultSet.getLong("user_id"));
                    user.setName(resultSet.getString("name"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setLogin(resultSet.getString("login"));
                    user.setPassword(resultSet.getString("password"));
                }
                Role role = Role.of(resultSet.getString("role_name"));
                role.setId(resultSet.getLong("role_id"));
                user.getRoles().add(role);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't get user", e);
        }
    }

    @Override
    public User create(User entity) throws DataProcessingExeption {
        String insertUserQuery = String.format("insert into %s.users "
                + "(name, surname, login, password) values (?, ?, ?, ?)", DB_NAME);
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(insertUserQuery,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setString(3, entity.getLogin());
            preparedStatement.setString(4, entity.getPassword());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't create user with login = "
                    + entity.getLogin(),  e);
        }
        entity.setRoles(addRoles(entity.getRoles(), entity.getId()));
        return entity;
    }

    private Set<Role> addRoles(Set<Role> roles, Long userId) throws DataProcessingExeption {
        for (Role role : roles) {
            String getRoleIdQuery = String.format("select role_id from %s.roles "
                    + "where role_name = ?", DB_NAME);
            try (PreparedStatement preparedStatement
                         = connection.prepareStatement(getRoleIdQuery)) {
                preparedStatement.setString(1, role.getRoleName().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    role.setId(resultSet.getLong("role_id"));
                }
            } catch (SQLException e) {
                throw new DataProcessingExeption("Can't add roles for user with user_id="
                        + userId, e);
            }

            String insertRoleQuery = String.format("insert into %s.users_roles "
                    + "(user_id, role_id) values(?, ?)", DB_NAME);
            try (PreparedStatement preparedStatement
                         = connection.prepareStatement(insertRoleQuery)) {
                preparedStatement.setLong(1, userId);
                preparedStatement.setLong(2, role.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DataProcessingExeption("Can't add roles to users_roles with user_id = "
                        + userId, e);
            }
        }
        return roles;
    }

    @Override
    public Optional<User> get(Long entityId) throws DataProcessingExeption {
        String query = String.format("select users.user_id, users.name, "
                + "users.surname, users.login, users.password,\n"
                + "roles.role_name, roles.role_id\n"
                + "from %1$s.users\n"
                + "join %1$s.users_roles using(user_id)\n"
                + "join %1$s.roles using(role_id)\n"
                + "where user_id = ?", DB_NAME);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, entityId);
            return getUser(preparedStatement);
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't get user with user_id = "
                    + entityId,  e);
        }
    }

    @Override
    public User update(User entity) throws DataProcessingExeption {
        String updateUserQuery = String.format("update %s.users set name = ?, "
                + "surname = ?, login = ?, password = ?\n"
                + "where user_id = ?", DB_NAME);
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateUserQuery)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setString(3, entity.getLogin());
            preparedStatement.setString(4, entity.getPassword());
            preparedStatement.setLong(5, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String deleteFromUsersRolesTable
                = String.format("delete from %s.users_roles where user_id = ?", DB_NAME);
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(deleteFromUsersRolesTable)) {
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't update user with user_id = "
                    + entity.getId(),  e);
        }
        entity.setRoles(addRoles(entity.getRoles(), entity.getId()));
        return entity;
    }

    @Override
    public boolean deleteById(Long entityId) throws DataProcessingExeption {
        String query = String.format(" delete users, users_roles from %1$s.users\n"
                + "left join %1$s.users_roles using(user_id)\n"
                + "where user_id = ?", DB_NAME);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, entityId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't delete user with user_id = "
                        + entityId, e);
        }
    }

    @Override
    public boolean delete(User entity) throws DataProcessingExeption {
        return deleteById(entity.getId());
    }

    @Override
    public List<User> getAll() throws DataProcessingExeption {
        String getAllUsersQuery = String.format(Locale.ROOT,
                "select users.user_id, users.name, users.surname, users.login, users.password, "
                        + "roles.role_name, roles.role_id\n"
                        + "from %1$s.users\n"
                        + "join %1$s.users_roles using(user_id)\n"
                        + "join roles using(role_id)\n"
                        + "order by users.user_id;", DB_NAME);
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(getAllUsersQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return getUsers(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't get all users", e);
        }
    }

    private List<User> getUsers(ResultSet resultSet) throws DataProcessingExeption {
        List<User> userListResult = new ArrayList<>();
        User user = null;
        try {
            while (resultSet.next()) {
                if (user == null) {
                    user = new User();
                }
                if (user.getId() != null && !user.getId().equals(resultSet.getLong("user_id"))) {
                    userListResult.add(user);
                    user = new User();
                }
                if (user.getId() == null) {
                    user.setId(resultSet.getLong("user_id"));
                    user.setName(resultSet.getString("name"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setLogin(resultSet.getString("login"));
                    user.setPassword(resultSet.getString("password"));
                }
                Role role = Role.of(resultSet.getString("role_name"));
                role.setId(resultSet.getLong("role_id"));
                user.getRoles().add(role);
            }
            userListResult.add(user);
            return userListResult;
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't get users", e);
        }
    }
}
