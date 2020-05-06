package rmi.provider;

import rmi.provider.service.UserService;
import rmi.provider.service.impl.UserServiceImpl;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/5/6 13:34
 */
public class RmiProvider {
    /**
     * 发布远程服务
     * @param args 传入参数
     */
    public static void main(String[] args) {
        try {
            //注册发布的远程服务的访问端口
            LocateRegistry.createRegistry(8888);

            //定义访问远程服务的URL
            String name="rmi://localhost:8888/rmi";

            //创建UserServiceImpl实现类对象,提供远程服务的对象
            UserService userService = new UserServiceImpl();

            Naming.bind(name, userService);

            System.out.println("--------发布远程服务-----------");

        } catch (RemoteException | MalformedURLException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
