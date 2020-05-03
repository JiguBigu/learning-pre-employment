package service;

import entity.User;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/5/3 13:46
 */
public interface UserService {

    /**
     * 根据用户id获取用户信息
     * @param id 用户id
     * @return 用户
     */
    User getUser(Long id);

}
