package filechannel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/12/22 13:38
 */
public class FileToFile {
    public static void main(String[] args) throws IOException {
        //获取被读文件的Channel
        FileInputStream fileInputStream = new FileInputStream("D://demoFile01");
        FileChannel inputStreamChannel = fileInputStream.getChannel();

        //获取据写入输出Channel
        FileOutputStream fileOutputStream = new FileOutputStream("D://demoFile02");
        FileChannel outputStreamChannel = fileOutputStream.getChannel();


        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //被读文件数据写入缓冲区
        int read;
        while ((read = inputStreamChannel.read(byteBuffer)) != -1) {
            byteBuffer.flip();
            outputStreamChannel.write(byteBuffer);
            byteBuffer.clear();
        }


        byteBuffer.flip();

        outputStreamChannel.write(byteBuffer);


        fileInputStream.close();
        fileOutputStream.close();

    }
}
