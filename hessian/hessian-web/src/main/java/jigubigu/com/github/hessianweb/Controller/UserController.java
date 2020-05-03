package jigubigu.com.github.hessianweb.Controller;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.UserService;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/5/3 14:03
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/user/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUser(id);
    }
}
