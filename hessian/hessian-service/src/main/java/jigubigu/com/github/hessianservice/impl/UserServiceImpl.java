package jigubigu.com.github.hessianservice.impl;

import entity.User;
import org.springframework.stereotype.Service;
import service.UserService;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/5/3 13:52
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(Long id) {
        User user = new User();
        user.setAgr(11);
        user.setId(id);
        user.setName("叽咕哔咕");
        return user;
    }
}
