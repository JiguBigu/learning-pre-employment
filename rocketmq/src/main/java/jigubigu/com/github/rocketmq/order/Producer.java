package jigubigu.com.github.rocketmq.order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/5/9 18:32
 */
public class Producer {

    public static final String NAMESERVER_ADDRESS = "129.211.28.51:9876";

    private static final String PRODUCER_GROUP = "demo_order_producer_group";

    public static final String DEMO_TOPIC = "demo_order_topic";

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        //1. 创建DefaultMQProducer
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);

        //2. 设置NameServer地址
        producer.setNamesrvAddr(NAMESERVER_ADDRESS);

        //3. 开启创建DefaultMQProducer
        producer.start();



        //4. 顺序发送消息
        //p1: 发送的消息信息
        //p2: 选中指定的消息队列对象（会将所有的消息队列传入进来）
        //p3: 指定对应的消息队列下标
        for(int i = 0; i< 5; i++){
            //5. 创建消息Message   (String topic, String tags,String key, byte[] body)
            Message message = new Message(DEMO_TOPIC,
                    //用于消息过滤
                    "tag1",
                    //消息唯一值
                    "key" + i,
                    "hello".getBytes(RemotingHelper.DEFAULT_CHARSET));

            SendResult res = producer.send(
                    message,
                    new MessageQueueSelector() {
                        @Override
                        public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                            //获取队列的下标
                            Integer index =(Integer) o;
                            //获取对应下标的队列
                            return list.get(index);
                        }
                    },
                    1
            );
            System.out.println(res);
        }

        //6. 关闭DefaultMQProducer
        producer.shutdown();
    }

}
