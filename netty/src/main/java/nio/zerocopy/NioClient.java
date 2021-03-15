package nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/11 17:42
 */
public class NioClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 7845));
        String fileName = "10628-Article Text-19763-1-10-20180216.pdf";

        //得到一个文件channel
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long starTIme = System.currentTimeMillis();
        // Linux 下调用transferTo 房卡可完成传输；Windows 下transferTo 只能发送8m，需要分段传输文件
        //transferTo 底层使用零拷贝
        long transferSize = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println(String.format("发送的总的字节数 = %d, 耗时%d", transferSize, System.currentTimeMillis() - starTIme));

    }
}
