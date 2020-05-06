package rmi.provider.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/5/6 13:31
 */
public interface UserService extends Remote {

    /**
     *
     * @param name 调用者名
     * @return hello字符串
     * @throws RemoteException 注意：这个异常一定要加，否则启动抛异常
     */
    String sayHello(String name) throws RemoteException;
}
