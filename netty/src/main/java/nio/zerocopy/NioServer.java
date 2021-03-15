package nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/11 17:35
 */
public class NioServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(7845));

        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 8);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();

            int read = 0;
            while ((read = socketChannel.read(buffer)) != -1) {
                //positon = 0, mark = -1
                buffer.rewind();

            }
        }

    }
}
