package top.mnsx.mnsx_chat.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public abstract class AbstractResponseMessage extends Message {
    // 是否成功
    private boolean success;
    // 理由
    private String reason;
}
