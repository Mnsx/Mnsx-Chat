package top.mnsx.mnsx_chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import top.mnsx.mnsx_chat.message.GroupChatRequestMessage;
import top.mnsx.mnsx_chat.message.GroupChatResponseMessage;
import top.mnsx.mnsx_chat.server.session.GroupSession;
import top.mnsx.mnsx_chat.server.session.GroupSessionFactory;

import java.util.List;

/**
 * @BelongsProject: mnsx_chat
 * @User: Mnsx_x
 * @CreateTime: 2022/11/20 9:57
 * @Description:
 */
@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        System.out.println("-----------------");
        if (groupSession.ifExistGroup(groupName)) {
            String from = msg.getFrom();
            List<Channel> channels = groupSession.getMembersChannel(groupName);
            for (Channel channel : channels) {
                channel.writeAndFlush(new GroupChatResponseMessage(msg.getFrom(), from + ": " + msg.getContent()));
            }
        } else {
            ctx.channel().writeAndFlush(new GroupChatResponseMessage(false, "群组不存在"));
        }
    }
}
