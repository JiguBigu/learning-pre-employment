package filechannel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/1/30 0:32
 */
public class MappedBufferDemo {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");
        //获取对应通道
        FileChannel channel = randomAccessFile.getChannel();
        //position: 可以直接修改的起始位置  size: 映射到内存的大小，即将1.txt的多少个字节映射到内存(5个字节)
        //直接修改的范围为[0,5)
        MappedByteBuffer mappedBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedBuffer.put(0, (byte) 'H');

        randomAccessFile.close();

    }
}
