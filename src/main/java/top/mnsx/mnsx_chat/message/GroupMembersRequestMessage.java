package top.mnsx.mnsx_chat.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupMembersRequestMessage extends Message {
    private String groupName;

    @Override
    public int getMessageType() {
        return GroupMembersRequestMessage;
    }
}
