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
 * @date 2021/4/19 0:47
 */
public class RepeatClientHandler extends SimpleChannelInboundHandler<String> {
    private static Logger logger = LoggerFactory.getLogger(RepeatClientHandler.class);

    /**
     * 创建完成时发送消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("喵喵喵喵\n");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(String.format("收到服务器消息:%s\n", msg));
        ctx.writeAndFlush(msg);
        Thread.sleep(200);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage());
    }
}
