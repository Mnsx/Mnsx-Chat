package top.mnsx.mnsx_chat.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequestMessage extends Message {
    // 接收者
    private String from;
    // 发送者
    private String to;
    // 消息内容
    private String content;

    @Override
    public int getMessageType() {
        return ChatRequestMessage;
    }
}
