package rmi.consumer;

import rmi.provider.service.UserService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/5/6 13:38
 */
public class RmiConsumer {

    public static void main(String[] args) {
        //定义访问远程服务的URL
        String name="rmi://localhost:8888/rmi";

        try {
            //通过url获得服务的远程代理对象
            UserService userService = (UserService) Naming.lookup(name);

            System.out.println("---" + userService.getClass() + "----");

            String res = userService.sayHello("叽咕哔咕");

            System.out.println("result:" + res);

        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
