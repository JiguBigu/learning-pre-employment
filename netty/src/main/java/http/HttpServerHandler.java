package http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.util.Scanner;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/23 16:11
 *  1. SimpleChannelInboundHandler 为ChannelInboundHandlerAdapter 子类
 *  2. HttpObject 浏览器和服务端通信的数据被封装成HttpObject
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    private static final String ICON = "/favicon.ico";
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject msg) throws Exception {
        // 判断msg 是不是一个httpRequest 请求
        if (msg instanceof HttpRequest) {
            System.out.println("msg 类型 = " + msg.getClass());
            System.out.println("客户端地址：" + channelHandlerContext.channel().remoteAddress());

            HttpRequest request = (HttpRequest) msg;
            URI uri = new URI(request.uri());
            if(ICON.equals(uri.getPath())) {
                System.out.println("请求了/favicon.ico 不做响应");
                return;
            }
            //回复信息给浏览器 [http协议]
            ByteBuf content = Unpooled.copiedBuffer("hello, 我是服务器", CharsetUtil.UTF_16);
            // 构造http 响应 [httpResponse]
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            //  设置返回的长度
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            channelHandlerContext.writeAndFlush(response);
        }
    }
}
