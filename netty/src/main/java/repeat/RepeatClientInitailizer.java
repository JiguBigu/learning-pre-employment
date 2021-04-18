package repeat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/4/19 0:45
 */
public class RepeatClientInitailizer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 设置字符串编码器
        pipeline.addLast(new StringEncoder());
        // 设置字符串解码器
        pipeline.addLast(new StringDecoder());
        // 设置客户端处理器
        pipeline.addLast(new RepeatClientHandler());
    }
}
