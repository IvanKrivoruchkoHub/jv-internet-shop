package mate.academy.internetshop.service.impl;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.anotations.Inject;
import mate.academy.internetshop.lib.anotations.Service;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private static UserDao userDao;

    @Override
    public User create(User user) {
        return userDao.create(user);
    }

    @Override
    public Optional<User> get(Long userId) {
        return userDao.get(userId);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public void delete(Long userId) {
        userDao.delete(userId);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }
}
