package filechannel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/12/22 13:20
 */
public class BufferOutput {
    public static void main(String[] args) throws IOException {
        String str = "Hello buffer";

        FileOutputStream fileOutputStream = new FileOutputStream("D://demoFile01");

        FileChannel fileChannel = fileOutputStream.getChannel();

        //构造缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();

        //缓冲区 与 channel交互
        fileChannel.write(byteBuffer);

        fileOutputStream.close();

    }
}
