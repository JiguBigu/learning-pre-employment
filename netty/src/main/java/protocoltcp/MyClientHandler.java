package protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/4/10 16:41
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final String word = "奶茶，豆腐，冰淇淋";
        for (int i = 1; i <= 10; i++) {
            String msgStr = word + i;
            byte[] content = msgStr.getBytes();

            MessageProtocol msg = new MessageProtocol();
            msg.setContent(content);
            msg.setLen(content.length);
            ctx.writeAndFlush(msg);
            System.out.println(System.currentTimeMillis() + " 客户端发送消息");
            Thread.sleep(200);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println(System.currentTimeMillis() + " 客户端收到消息:" + msg);
    }
}
