package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/15 11:18
 */
public class Server2 {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private final static int PORT = 8721;

    public Server2() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8972));
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 4);

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();

            int read = 0;
            while ((read = socketChannel.read(buffer)) != 0) {
                System.out.println(new String(buffer.array()));
                buffer.rewind();
            }
        }

    }

}
