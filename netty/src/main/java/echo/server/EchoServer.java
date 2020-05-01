package echo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/4/30 3:33
 */
public class EchoServer {
    private final static int PORT = 8080;

    public void start() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        //空的构造方法
        ServerBootstrap bootstrap = new ServerBootstrap();
        //通过group方法创建ServerBootstrap
        bootstrap.group(group)
                //指定nio传输的channel
                .channel(NioServerSocketChannel.class)
                //设置socket地址的端口
                .localAddress(PORT)
                //添加 EchoServerHandler 到 Channel 的 ChannelPipelin
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch)  {
                        ch.pipeline()
                                //添加行拆包器
                                .addLast(new LineBasedFrameDecoder(Integer.MAX_VALUE))
                                .addLast(new EchoServerHandler());
                    }
                });

        //绑定的服务器,sync 等待服务器关闭
        ChannelFuture future = bootstrap.bind().sync();

        System.out.println(EchoServer.class.getName() + " started and listen on " + future.channel().localAddress());

        //关闭 channel 和 块，直到它被关闭
        future.channel().closeFuture().sync();

        //关机的 EventLoopGroup，释放所有资源。
        group.shutdownGracefully().sync();

    }

    public static void main(String[] args) throws InterruptedException {
        new EchoServer().start();
    }

}
