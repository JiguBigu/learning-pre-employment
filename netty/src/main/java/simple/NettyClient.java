package simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/20 14:29
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        // 客户端需要一个事件循环组
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        // 创建一个客户端的启动对象
        Bootstrap bootstrap = new Bootstrap();
        try {
            //设置相关参数
            // 设置线程组
            bootstrap.group(eventExecutors)
                    // 设置客户端通道实现类
                    .channel(NioSocketChannel.class)
                    // 设置客户端处理程序
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            // 启动客户端，去连接服务端
            // channelFuture 涉及到netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully();
        }

    }
}
