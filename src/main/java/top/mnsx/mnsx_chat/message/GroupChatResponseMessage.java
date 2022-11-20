package top.mnsx.mnsx_chat.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class GroupChatResponseMessage extends AbstractResponseMessage {
    // 发送者
    private String from;
    // 消息内容
    private String content;

    public GroupChatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return GroupChatResponseMessage;
    }
}
