package jigubigu.com.github.rocketmq.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/5/9 20:18
 */
public class TransactionListenerImpl implements TransactionListener {

    /**
     * 存储对应事务信息  key：事务Id  value：当前事务执行的状态
     */
    private ConcurrentHashMap<String, Integer> loaclTrans = new ConcurrentHashMap<>();

    /**
     * 本地事务执行中
     */
    private final static int RUNNING = 0;
    /**
     * 本地事务执行成功
     */
    private final static int SUCCESS = 1;
    private final static int FAILURED = 2;


    /**
     * 执行本地事务
     * @param message
     * @param o
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        //事务Id
        String transactionId = message.getTransactionId();

        //业务执行，处理本地事务（调取service）
        loaclTrans.put(transactionId, RUNNING);

        try {
            System.out.println("处理本地事务....");
            Thread.sleep(6100);

            loaclTrans.put(transactionId, SUCCESS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            loaclTrans.put(transactionId, FAILURED);

            return LocalTransactionState.ROLLBACK_MESSAGE;
        }


        return LocalTransactionState.COMMIT_MESSAGE;
    }

    /**
     * 消息回查
     * @param messageExt
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        //获取事务Id
        String transactionId = messageExt.getTransactionId();

        //获取事务状态
        Integer status = loaclTrans.get(transactionId);

        System.out.println("------消息回查-----\n" + "事务Id：" + transactionId + "\t事务状态: " + status);

        switch (status){
            case SUCCESS:
                return LocalTransactionState.COMMIT_MESSAGE;
            case FAILURED:
                return LocalTransactionState.ROLLBACK_MESSAGE;

                default:
                    return LocalTransactionState.UNKNOW;

        }
    }
}
