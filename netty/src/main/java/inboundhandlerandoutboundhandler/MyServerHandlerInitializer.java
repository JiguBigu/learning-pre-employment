package inboundhandlerandoutboundhandler;

import http.ServerInitializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/4/8 16:45
 */
public class MyServerHandlerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new MyByteToLongDecoder())
                .addLast(new MyLongToByteEncoder())
                .addLast(new MyServerHandler());
    }
}
