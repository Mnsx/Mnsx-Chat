package top.mnsx.mnsx_chat.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import top.mnsx.mnsx_chat.message.GroupChatRequestMessage;
import top.mnsx.mnsx_chat.message.GroupQuitRequestMessage;

/**
 * @BelongsProject: mnsx_chat
 * @User: Mnsx_x
 * @CreateTime: 2022/11/20 9:57
 * @Description:
 */
@ChannelHandler.Sharable
public class GroupQuitRequestMessageHandler extends SimpleChannelInboundHandler<GroupQuitRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitRequestMessage msg) throws Exception {

    }
}
