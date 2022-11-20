package top.mnsx.mnsx_chat.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class ProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {
    // 协议内容最大字节数
    private static final int MAX_FRAME_LENGTH = 1024;
    // 长度字段偏移量
    private static final int LENGTH_FIELD_OFFSET = 12;
    // 长度字段长度
    private static final int LENGTH_FIELD_LENGTH = 4;
    // 长度字段间隔多少字节，是正文
    private static final int LENGTH_ADJUSTMENT = 0;
    // 截取多少字节，作为正文
    private static final int INITIAL_BYTES_TO_STRIP = 0;

    public ProtocolFrameDecoder() {
        this(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH, LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP);
    }

    public ProtocolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
}
