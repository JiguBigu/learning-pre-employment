package echo.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/4/30 3:46
 */
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     * 建立连接后该 channelActive() 方法被调用一次
     * 一旦建立了连接，字节序列被发送到服务器。
     * 这里发送“Netty rocks!” 通过覆盖这种方法，我们确保东西被尽快写入到服务器
     * @param context
     */
    @Override
    public void channelActive(ChannelHandlerContext context){
        //当被通知该 channel 是活动的时候就发送信息
        for(int i = 0; i < 100; i++){
            context.writeAndFlush(
                    //为行拆包器构造尾部
                    Unpooled.copiedBuffer("这是第 " + i + "条消息\r\n", CharsetUtil.UTF_8));
        }
    }

    /**
     * 方法会在接收到数据时被调用。
     * 注意，由服务器所发送的消息可以以块的形式被接收。
     * 即，当服务器发送 5 个字节是不是保证所有的 5 个字节会立刻收到
     * 即使是只有 5 个字节，channelRead0() 方法可被调用两次，
     * 第一次用一个ByteBuf（Netty的字节容器）装载3个字节和第二次一个 ByteBuf 装载 2 个字节。
     * 唯一要保证的是，该字节将按照它们发送的顺序分别被接收(TCP 协议的可靠性）
     * @param channelHandlerContext
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        //打印客户端接收到的消息
        System.out.println("Client received: " + byteBuf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 记录错误日志并关闭channel
     * @param context
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        context.close();
    }
}
