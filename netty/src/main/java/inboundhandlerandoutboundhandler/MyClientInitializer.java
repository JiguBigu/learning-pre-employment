package inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/4/8 16:50
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                // 对出站数据进行编码
                .addLast(new MyLongToByteEncoder())
                .addLast(new MyByteToLongDecoder())
                .addLast(new MyClientHandler());
    }
}
