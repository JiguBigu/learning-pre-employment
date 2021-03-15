package filechannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/1/30 1:06
 */

/**
 * telnet 127.0.0.1 7000
 * Ctrl + ]
 * send hello world
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) throws IOException {
        // 使用ServerSocketChannel 和 SocketChannel网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        // 绑定端口到Socket, 并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        // 创建一个Buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        // 等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();

        // 循环读取
        //从客户端接受8个字节
        int msgLen = 8;
        while (true) {
            int byteRead = 0;
            while (byteRead < msgLen) {
                long read = socketChannel.read(byteBuffers);
                byteRead += read;
                System.out.println("byteRead = " + byteRead);
                //流打印,看看当前buffers
                Arrays.stream(byteBuffers).map(buffer -> "position = " + buffer.position() +
                        ",limit = " + buffer.limit()).forEach(System.out::println);
            }
            //将所有的buffer反转
            Arrays.stream(byteBuffers).forEach(ByteBuffer::flip);

            //将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite < msgLen) {
                //回送到客户端
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;
            }
            //所有buffer进行clear
            Arrays.stream(byteBuffers).forEach(ByteBuffer::clear);

            System.out.println("byteRead = " + byteRead + ",byteWrite = " + byteWrite + ",messageLength = " + msgLen);
        }

    }
}
