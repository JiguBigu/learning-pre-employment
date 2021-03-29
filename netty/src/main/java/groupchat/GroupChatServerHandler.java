package groupchat;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/28 17:10
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 定义一个ChannelGroup用于管理所有的channel，GlobalEventExecutor是一个全局事件执行器，单例模式
     */
    private static ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * handlerAdded 表示建立连接，一旦连接建立，就会首先执行这个方法
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        CHANNEL_GROUP.writeAndFlush(String.format("%s [%s]加入了聊天组", channel.remoteAddress(), sdf.format(new Date())));
        CHANNEL_GROUP.add(channel);
    }

    /**
     * 表示channel 处于活动状态
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(String.format("[%s]上线\n", ctx.channel().remoteAddress()));
    }

    /**
     * 表示channel 处于非活动状态
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(String.format("%s [%s]离线\n", channel.remoteAddress(), sdf.format(new Date())));
        // 自动离开 所以不需要：CHANNEL_GROUP.remove(channel);
        CHANNEL_GROUP.writeAndFlush(String.format("[%s]离开\n", channel.remoteAddress()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();
        channel.writeAndFlush(String.format("[我]:%s", s));
        CHANNEL_GROUP.stream().filter(ch -> ch != channel).forEach(targetChannel ->
                targetChannel.writeAndFlush(String.format("[%s]:%s", channel.remoteAddress(), s)));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
