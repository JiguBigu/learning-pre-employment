package simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/18 14:41
 */

/**
 *  自定义一个handler 需要绑定一个netty 规定好的handlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf message = (ByteBuf) msg;
        System.out.println("server received: " + message.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址是:" + ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完毕
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 数据写入缓存并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("我收到你的消息了喵", CharsetUtil.UTF_8));
    }

    /**
     * 处理异常，一般是关闭通道
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
