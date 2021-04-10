package tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/4/10 15:49
 */
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 40; i++) {
            ByteBuf byteBuf = Unpooled.copiedBuffer("client-message-" + i, CharsetUtil.UTF_8);
            ctx.writeAndFlush(byteBuf);
        }
        System.out.println("客户端发送消息...");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        System.out.println("收到服务端消息:" + new String(buffer, Charset.forName("utf-8")));
    }
}
