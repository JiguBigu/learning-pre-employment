package jigubigu.com.github.rocketmq.transaction;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/5/9 18:32
 */
public class TransactionProducer {

    public static final String NAMESERVER_ADDRESS = "129.211.28.51:9876";

    private static final String PRODUCER_GROUP = "demo_transaction_producer_group";

    public static final String DEMO_TOPIC = "demo_transaction_topic";

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        //1. TransactionMQProducer
        TransactionMQProducer producer = new TransactionMQProducer(PRODUCER_GROUP);

        //2. 设置NameServer地址
        producer.setNamesrvAddr(NAMESERVER_ADDRESS);

        //指定消息监听对象， 用于执行本地事务和消息会查
        TransactionListener listener = new TransactionListenerImpl();
        producer.setTransactionListener(listener);

        //线程池
        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                5,
                100,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2000),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("client-transaction-msg-check-thread");
                        return thread;
                    }
                }
        );
        producer.setExecutorService(executorService);

        //3. 开启创建DefaultMQProducer
        producer.start();

        //4. 创建消息Message   (String topic, String tags,String key, byte[] body)
        Message message = new Message(DEMO_TOPIC,
                //用于消息过滤
                "tag1",
                //消息唯一值
                "key",
                "hello transaction".getBytes(RemotingHelper.DEFAULT_CHARSET));

        //5. 发送事务消息
        TransactionSendResult res = producer.sendMessageInTransaction(message, "hello transaction");

        //6. 关闭DefaultMQProducer
        producer.shutdown();
    }

}
