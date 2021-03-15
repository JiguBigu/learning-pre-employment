package nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/11 13:58
 * Attribute ：
 * 1. 连接服务器
 * 2. 发送消息
 * 3. 接收服务器消息
 */

public class GroupChatClient {
    private final static String HOST = "127.0.0.1";
    private final static int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            //设置非阻塞
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(String.format("%s准备就绪！", username));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        msg = username + "说：" + msg;
        ByteBuffer msgBuffer = ByteBuffer.wrap(msg.getBytes());
        try {
            socketChannel.write(msgBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMsg() {
        try {
            int select = selector.select();
            if (select > 0) {
//                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
//                while (iterator.hasNext()) {
//                    SelectionKey selectionKey = iterator.next();
//                    if (selectionKey.isReadable()) {
//                        SelectableChannel channel = selectionKey.channel();
//                        if (channel instanceof SocketChannel) {
//                            try {
//                                ByteBuffer buffer = ByteBuffer.allocate(1024);
//                                int read = ((SocketChannel) channel).read(buffer);
//                                if (read > 0) {
//                                    System.out.println(new String(buffer.array()));
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                    iterator.remove();
//                }
                selector.selectedKeys().forEach(selectionKey -> {
                    if (selectionKey.isReadable()) {
                        SelectableChannel channel = selectionKey.channel();
                        if (channel instanceof SocketChannel) {
                            try {
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                int read = ((SocketChannel) channel).read(buffer);
                                if (read > 0) {
                                    System.out.println(new String(buffer.array()));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    selector.selectedKeys().remove(selectionKey);
                });
            } else {
                System.out.println("无可用通道....");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatClient client = new GroupChatClient();

        new Thread(() -> {
            while (true) {
                client.readMsg();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String msg = scanner.next();
            client.sendMsg(msg);
        }
    }
}
