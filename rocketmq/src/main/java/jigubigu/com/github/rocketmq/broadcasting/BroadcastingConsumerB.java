package jigubigu.com.github.rocketmq.broadcasting;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/5/9 19:40
 */

/**
 * 默认消费模式是集群消费模式，这个是广播模式
 */
public class BroadcastingConsumerB {

    private static final String PRODUCER_GROUP = "demo_broadcasting_consumer_group";

    public static void main(String[] args) throws MQClientException {
        //1. 创建DefaultMQPushConsumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(PRODUCER_GROUP);

        //2. 设置NameServer地址
        consumer.setNamesrvAddr(BroadcastingProducer.NAMESERVER_ADDRESS);

        //设置广播模式
        consumer.setMessageModel(MessageModel.BROADCASTING);

        //设置消息拉取最大数
        consumer.setConsumeMessageBatchMaxSize(2);

        //3. 设置subscribe,这里是读取的主要信息
        consumer.subscribe(BroadcastingProducer.DEMO_TOPIC, "*");

        //4. 创建消息监听MessageListener
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt msg : list) {
                    try {
                        //获取主题
                        String topic = msg.getTopic();
                        //获取标签
                        String tags = msg.getTags();
                        //获取信息
                        String res = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);

                        System.out.println("广播消费B-------Consumer消费信息：" +
                                "topic:" + topic + "\ntags:" + tags + "\nresult:" + res);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        //消息重试
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                //5. 消息消费完成,返回状态
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //6. 开启DefaultMQPushConsumer
        consumer.start();


    }
}
