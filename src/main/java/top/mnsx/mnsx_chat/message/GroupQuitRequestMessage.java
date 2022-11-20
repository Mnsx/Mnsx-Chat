package top.mnsx.mnsx_chat.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupQuitRequestMessage extends Message {
    // 组名
    private String groupName;

    // 用户名
    private String username;

    @Override
    public int getMessageType() {
        return GroupQuitRequestMessage;
    }
}
