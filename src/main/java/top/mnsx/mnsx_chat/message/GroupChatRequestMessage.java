package top.mnsx.mnsx_chat.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupChatRequestMessage extends Message {
    // 发送者
    private String from;
    // 群组名称
    private String groupName;
    // 消息内容
    private String content;

    @Override
    public int getMessageType() {
        return GroupChatRequestMessage;
    }
}
