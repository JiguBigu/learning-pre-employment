package rmi.provider.service.impl;

import rmi.provider.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/5/6 13:32
 */
public class UserServiceImpl extends UnicastRemoteObject implements UserService {

    public UserServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String sayHello(String name) throws RemoteException{
        return "hello " + name + " by dubbo RMI!";
    }
}
