package top.mnsx.mnsx_chat.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import top.mnsx.mnsx_chat.message.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> outList) throws Exception {
        ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
        // 魔数
        out.writeBytes(new byte[]{1, 2, 3, 4});
        // 版本号
        out.writeByte(1);
        // 序列化算法 jdk 0 json 1
        out.writeByte(0);
        // 指令类型
        out.writeByte(msg.getMessageType());
        // 请求序号
        out.writeInt(msg.getSequenceId());
        // 填充
        out.writeByte(0xff);
        // 准备正文
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(msg);
        byte[] bytes = baos.toByteArray();
        // 正文长度
        out.writeInt(bytes.length);
        // 消息正文
        out.writeBytes(bytes);
        outList.add(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 获取数据
        int magicNum = in.readInt();
        byte version = in.readByte();
        byte serializerType = in.readByte();
        byte messageType = in.readByte();
        int sequenceId = in.readInt();
        in.readByte();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Message message = (Message) objectInputStream.readObject();
        System.out.println(
                magicNum + "\t" + version + "\t"
                + serializerType + "\t" + messageType
                + "\t" + sequenceId + "\t" + length
        );
        System.out.println(message);
        out.add(message);
    }
}
