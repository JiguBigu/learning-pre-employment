package repeat;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/4/19 0:39
 */
public class RepeatServerHandler extends SimpleChannelInboundHandler<String> {
    private static Logger logger = LoggerFactory.getLogger(RepeatServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(String.format("服务端收到字符串消息:%s", msg));
        // 会送消息
        ctx.writeAndFlush(String.format("我收到你发送的消息:%s", msg));
        Thread.sleep(100);
    }
}
