package codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/18 14:41
 * 自定义一个handler 需要绑定一个netty 规定好的handlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MyDataInfo.MyMessage message = (MyDataInfo.MyMessage) msg;
        if (MyDataInfo.MyMessage.DataType.StudentType.equals(message.getDataType())) {
            MyDataInfo.Student student = message.getStudent();
            System.out.println(String.format("客户端发送的名字 = %s, 编号是 = %s", student.getName(), student.getId()));
        } else if(MyDataInfo.MyMessage.DataType.WorkerType.equals(message.getDataType())) {
            MyDataInfo.Worker worker = message.getWorker();
            System.out.println(String.format("客户端发送给的名字 = %s, 年龄是 = %s", worker.getName(), worker.getAge()));
        } else {
            System.out.println("传输数据类型不符合预期,do something...");
        }
    }

    /**
     * 数据读取完毕
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 数据写入缓存并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("我收到你的消息了喵", CharsetUtil.UTF_8));
    }

    /**
     * 处理异常，一般是关闭通道
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
