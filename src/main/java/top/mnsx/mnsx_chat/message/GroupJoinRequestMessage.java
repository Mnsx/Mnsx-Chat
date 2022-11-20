package top.mnsx.mnsx_chat.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupJoinRequestMessage extends Message {
    // 群组名称
    private String groupName;
    // 成员名称
    private String username;

    @Override
    public int getMessageType() {
        return GroupJoinRequestMessage;
    }
}
