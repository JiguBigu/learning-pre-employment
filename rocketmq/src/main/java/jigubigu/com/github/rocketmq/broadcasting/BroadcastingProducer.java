package jigubigu.com.github.rocketmq.broadcasting;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/5/9 18:32
 */
public class BroadcastingProducer {

    public static final String NAMESERVER_ADDRESS = "129.211.28.51:9876";

    private static final String PRODUCER_GROUP = "demo_broadcasting_producer_group";

    public static final String DEMO_TOPIC = "demo_broadcasting_topic";

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        //1. 创建DefaultMQProducer
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);

        //2. 设置NameServer地址
        producer.setNamesrvAddr(NAMESERVER_ADDRESS);

        //3. 开启创建DefaultMQProducer
        producer.start();

        //4. 批量发送消息
        List<Message> messages = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Message message = new Message(DEMO_TOPIC,
                    //用于消息过滤
                    "tag1",
                    //消息唯一值
                    "key1" + i,
                    ("hello broadcasting " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            messages.add(message);
        }

        //5. 批量发送消息
        SendResult res = producer.send(messages);
        System.out.println(res);

        //6. 关闭DefaultMQProducer
        producer.shutdown();
    }

}
