package simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

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

        // 比如有一个耗时非常长的业务 -> 异步执行 -> 提交该channel 对应的NioEventLoop 的 taskQueue中
        // 方案一 用户自定义任务 提交到taskQueue中
        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(1000 * 2);
                ctx.writeAndFlush(Unpooled.copiedBuffer("喵1 ", CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(3 * 1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("喵2 ", CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 用户定时任务 提交到scheduleTaskQueue中
        ctx.channel().eventLoop().schedule(() -> {
            try {
                Thread.sleep(2 * 1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("喵3 ", CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 5, TimeUnit.SECONDS);
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
