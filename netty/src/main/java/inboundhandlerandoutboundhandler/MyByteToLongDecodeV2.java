package inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/4/10 14:41
 * 相对ByteToMessageDecoder 方便，但有一些局限性
 *  1. 并不是所以的ByteBuf 操作都支持，假如调用了不被支持的方法，会抛出UnsupportedOperationException
 *  2. ReplayingDecoder 可能会稍慢于 ByteToMessageDecoder, 假如网络缓慢或者消息格式复杂时，消息会被拆成多个碎片，速度慢
 */
public class MyByteToLongDecodeV2 extends ReplayingDecoder<Void> {
    /**
     * 在ReplayingDecoder 中不需要判断数据是否够读取，即不需要使用in.readableBytes() >= 8来判断是否有足够的数据
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecodeV2 被调用");
        out.add(in.readLong());
    }
}
