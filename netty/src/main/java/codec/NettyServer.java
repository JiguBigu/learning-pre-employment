package codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/18 14:18
 */
public class NettyServer {
    public static final int PORT = 6668;

    public static void main(String[] args) throws InterruptedException {
        // 1. 创建bossGroup 和 workerGroup,
        // bossGroup 和 workGroup 含有的子线程(NioEventLoop)个数默认为 实际CPU核数 * 2
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 2. 创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 3. 使用链式编程来进行配置
            bootstrap.group(bossGroup, workerGroup)
                    // 使用NioSocketChannel 作为服务器通道实现
                    .channel(NioServerSocketChannel.class)
                    // 设置线程队列等待连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 为workerGroup 的 eventLoop 的对应管道 设置处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    //!!--指定对哪种对象进行解码
                                    .addLast("decoder", new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()))
                                    .addLast(new NettyServerHandler());
                        }
                    });
            // 绑定端口并/同步处理，并启动服务器
            // netty中write bind connect都是异步，会返回一个channelFuture
            ChannelFuture channelFuture = bootstrap.bind(PORT).sync();
            channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("监听端口成功");
                    } else {
                        System.out.println("监听端口失败");
                    }
                }
            });
            // 通过channelFuture 拿到bootstrap 配置
            channelFuture.channel().config().getOptions().forEach((key, value) -> System.out.println(String.format("key: %s, value: %s", key, value)));
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
