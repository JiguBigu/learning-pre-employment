package heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/29 19:19
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    /**
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            // 向下转型
            IdleStateEvent event = (IdleStateEvent) evt;
            String msg = null;
            switch (event.state()) {
                case READER_IDLE:
                    msg = "发生了读空闲";
                    break;
                case WRITER_IDLE:
                    msg = "发生了写空闲";
                    break;
                case ALL_IDLE:
                    msg = "发生了读写空闲";
                default:
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + ":" + msg);
            // todo 服务器相应处理
        }
    }
}
