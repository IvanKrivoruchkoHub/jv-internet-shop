package mate.academy.internetshop.dao;

import mate.academy.internetshop.model.User;

import java.util.Optional;

public interface UserDao {
    User create(User user);

    Optional<User> get(Long userId);

    User update(User user);

    void delete(Long userId);

    void delete(User user);
}
