package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.exceptions.DataProcessingExeption;
import mate.academy.internetshop.lib.anotations.Inject;
import mate.academy.internetshop.lib.anotations.Service;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.util.HashUtil;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private static UserDao userDao;

    @Override
    public User create(User user) throws DataProcessingExeption {
        user.setSalt(HashUtil.getSalt());
        String hashedPassword = HashUtil.hashPassword(user.getPassword(), user.getSalt());
        user.setPassword(hashedPassword);
        return userDao.create(user);
    }

    @Override
    public User get(Long userId) throws DataProcessingExeption {
        return userDao.get(userId)
                .orElseThrow(() -> new NoSuchElementException("Can't find user with id " + userId));
    }

    @Override
    public User update(User user) throws DataProcessingExeption {
        return userDao.update(user);
    }

    @Override
    public boolean deleteById(Long userId) throws DataProcessingExeption {
        return userDao.deleteById(userId);
    }

    @Override
    public boolean delete(User user) throws DataProcessingExeption {
        return userDao.delete(user);
    }

    @Override
    public List<User> getAll() throws DataProcessingExeption {
        return userDao.getAll();
    }

    @Override
    public User login(String login, String password)
            throws AuthenticationException, DataProcessingExeption {
        Optional<User> user = userDao.findByLogin(login);
        if (user.isEmpty() || !user.get().getPassword()
                .equals(HashUtil.hashPassword(password, user.get().getSalt()))) {
            throw new AuthenticationException("Incorrect login or password");
        }
        return user.get();
    }
}
