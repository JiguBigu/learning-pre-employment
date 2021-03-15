package nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/11 13:02
 * @Attribute 1. 服务器启动并监听6667端口  2. 接收并转发消息，提示用户上线信息
 */
public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listener;
    private static final int PORT = 6667;

    /**
     * 构造器
     */
    public GroupChatServer() {
        try {
            //得到选择器
            selector = Selector.open();
            //ServerSocketChannel
            listener = ServerSocketChannel.open();
            //绑定端口
            listener.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞
            listener.configureBlocking(false);
            //将listener注册到选择器上
            listener.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            while (true) {
                int select = selector.select(2000);
                // 无事件处理
                if ( select == 0) {
                    System.out.println("等待...");
                } else {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //取出selectionKey
                        SelectionKey key = iterator.next();

                        //监听到accept
                        if (key.isAcceptable()) {
                            SocketChannel sc = listener.accept();
                            sc.configureBlocking(false);
                            //将socketChannel注册到selector上
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress() + "上线");
                        }
                        //监听到读事件
                        if (key.isReadable()) {
                            readMsg(key);
                        }
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取到数据的处理操作
     * @param key
     */
    private  void readMsg(SelectionKey key) {
        SocketChannel channel = null;
        try {
            //获取socketChannel
            channel = (SocketChannel) key.channel();
            //创建缓冲区
            //消息不超过1024个字节
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = channel.read(buffer);
            if (read > 0) {
                //读取到数据，把收到的数据转成字符串输出
                String msg = new String(buffer.array());
                //服务端打印
                System.out.println(String.format("服务器收到消息:%s\t from %s", msg, channel.getRemoteAddress()));
                //转发消息给其他客户端
                sendMsgToClient(msg, channel);
            }

        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "下线了");
                channel.close();
                key.cancel();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 转发消息给出自己以外的客户端
     * @param msg 消息
     * @param except 自己的socketChannel
     */
    private void sendMsgToClient(String msg, SocketChannel except) {
        //遍历所有除自己以外的socketChannel
        selector.keys().forEach(selectionKey -> {
            SelectableChannel targetChannel = selectionKey.channel();
            //排除自己
            if(targetChannel instanceof SocketChannel && targetChannel != except) {
                try {
                    ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                    ((SocketChannel) targetChannel).write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static void main(String[] args) {
        new GroupChatServer().listen();
    }
}
