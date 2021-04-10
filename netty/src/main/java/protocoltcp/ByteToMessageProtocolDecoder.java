package protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/4/10 16:35
 */
public class ByteToMessageProtocolDecoder extends ReplayingDecoder<MessageProtocol> {
    private int count;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("ByteToMessageProtocolDecoder decode 被调用 - " + ++count);

        // 从ByteBuf中读取数据，二进制字节码 -> MessageProtocol数据包（对象）
        int len = in.readInt();
        byte[] content = new byte[len];
        in.readBytes(content);

        MessageProtocol msg = enrichMessageProtocol(len, content);
        out.add(msg);
    }

    private MessageProtocol enrichMessageProtocol(int len, byte[] content) {
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(len);
        messageProtocol.setContent(content);
        return messageProtocol;
    }
}
