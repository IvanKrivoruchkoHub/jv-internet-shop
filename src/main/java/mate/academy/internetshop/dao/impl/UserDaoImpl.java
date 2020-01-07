package mate.academy.internetshop.dao.impl;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.IdGenerator;
import mate.academy.internetshop.lib.anotations.Dao;
import mate.academy.internetshop.model.User;

import java.util.NoSuchElementException;
import java.util.Optional;

@Dao
public class UserDaoImpl implements UserDao {

    @Override
    public User create(User user) {
        user.setId(IdGenerator.getUserId());
        Storage.users.add(user);
        return user;
    }

    @Override
    public Optional<User> get(Long userId) {
        return Storage.users
                .stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst();
    }

    @Override
    public User update(User user) {
        User userToUpdate = get(user.getId())
                .orElseThrow(() -> new NoSuchElementException("Can`t find user to update"));
        userToUpdate.setName(user.getName());
        return userToUpdate;
    }


    @Override
    public void delete(Long userId) {
        delete(get(userId).orElseThrow(()
                -> new NoSuchElementException("Can`t find user with id" + userId)));
    }

    @Override
    public void delete(User user) {
        if (!Storage.users.remove(user)) {
            throw new NoSuchElementException("Can`t find user with id" + user.getId());
        }
    }
}
