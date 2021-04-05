package websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/4/3 14:45
 * TextWebSocketFrame 表示一个文本帧(frame
 */
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.out.println("服务器收到消息:" + textWebSocketFrame.text());
        // 回复消息
        Channel channel = channelHandlerContext.channel();
        channel.writeAndFlush(new TextWebSocketFrame("服务器时间" + LocalDateTime.now() + ":" + textWebSocketFrame.text()));
    }

    /**
     * web客户端连接后触发
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // longText 是唯一的， shortText 有可能重复不唯一
        System.out.println("handlerAdded 调用" + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemover 被调用" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生：" + cause.getMessage());
        ctx.close();
    }
}
