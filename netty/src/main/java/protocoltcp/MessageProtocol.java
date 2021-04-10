package protocoltcp;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/4/10 16:32
 */
public class MessageProtocol {
    private int len;
    private byte[] content;


    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageProtocol{" +
                "长度=" + len +
                ", 内容=" + new String(content, Charset.forName("utf8")) +
                '}';
    }
}
