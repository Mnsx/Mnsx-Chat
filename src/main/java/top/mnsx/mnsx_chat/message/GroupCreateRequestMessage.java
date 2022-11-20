package top.mnsx.mnsx_chat.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreateRequestMessage extends Message {
    // 群组名称
    private String groupName;
    // 成员信息
    private Set<String> members;

    @Override
    public int getMessageType() {
        return GroupCreateRequestMessage;
    }
}
