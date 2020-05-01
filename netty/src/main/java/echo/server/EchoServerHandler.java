package echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/4/30 3:28
 */

/**
 * 该注解标识该类的实例之间可以在 channel 里面共享
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx,
                            Object msg) {
        ByteBuf in = (ByteBuf) msg;
        //日志输出消息到控制台
        System.out.println("Server received:\n " + in.toString(CharsetUtil.UTF_8) + "\n-----Server received end!-------");
        //将所接收的消息返回给发送者。这还没有冲刷数据
        ctx.write(in);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //冲刷所有待审消息到远程节点。关闭通道后，操作完成
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        //打印异常堆栈跟踪
        cause.printStackTrace();
        //关闭通道
        ctx.close();
    }
}
