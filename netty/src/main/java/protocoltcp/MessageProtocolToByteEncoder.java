package protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/4/10 16:33
 */
public class MessageProtocolToByteEncoder  extends MessageToByteEncoder<MessageProtocol> {
    private int count;

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MessageProtocolToByteEncoder encode被调用 - " + ++count);
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}
