package protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/4/10 16:46
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println("服务端收到消息:" + msg);

        byte[] content = UUID.randomUUID().toString().getBytes();
        int len = content.length;
        MessageProtocol responseMsg = new MessageProtocol();
        responseMsg.setLen(len);
        responseMsg.setContent(content);
        ctx.writeAndFlush(responseMsg);
    }
}
