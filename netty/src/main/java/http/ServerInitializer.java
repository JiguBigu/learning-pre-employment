package http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/23 16:13
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 向管道加入处理器
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 加入一个netty 提供的解码器 httpServerCodec codec -> [encoder - decoder]
        //  netty 提供的
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        pipeline.addLast("MyHttpServerHandler", new HttpServerHandler());
    }
}
