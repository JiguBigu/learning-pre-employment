package filechannel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/12/22 14:03
 */
public class FileToFile02 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("D://demoFile01");
        FileOutputStream fileOutputStream = new FileOutputStream("D://demoFile02");

        FileChannel inputStreamChannel = fileInputStream.getChannel();
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        //拷贝 不超过2G
        outputStreamChannel.transferFrom(inputStreamChannel, 0, inputStreamChannel.size());

        //关闭
        inputStreamChannel.close();
        outputStreamChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
