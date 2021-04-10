package protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/4/10 15:42
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new MessageProtocolToByteEncoder())
                .addLast(new ByteToMessageProtocolDecoder())
                .addLast(new MyServerHandler());

    }
}
