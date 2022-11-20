package top.mnsx.mnsx_chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import top.mnsx.mnsx_chat.message.GroupChatRequestMessage;
import top.mnsx.mnsx_chat.message.GroupCreateRequestMessage;
import top.mnsx.mnsx_chat.message.GroupCreateResponseMessage;
import top.mnsx.mnsx_chat.server.session.Group;
import top.mnsx.mnsx_chat.server.session.GroupSession;
import top.mnsx.mnsx_chat.server.session.GroupSessionFactory;

import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: mnsx_chat
 * @User: Mnsx_x
 * @CreateTime: 2022/11/20 9:57
 * @Description:
 */
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Set<String> members = msg.getMembers();
        // 群管理器
        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        Group group = groupSession.createGroup(groupName, members);
        if (group == null) {
            // 发送拉群消息
            List<Channel> channels = groupSession.getMembersChannel(groupName);
            for (Channel channel : channels) {
                channel.writeAndFlush(new GroupCreateResponseMessage(true, "您已被拉入" + groupName));
            }
            // 发送创建成功消息
            ctx.channel().writeAndFlush(new GroupCreateResponseMessage(true, groupName + "创建成功"));
        } else {
            ctx.channel().writeAndFlush(new GroupCreateResponseMessage(false, groupName + "创建失败, 群名已经重复"));
        }
    }
}
