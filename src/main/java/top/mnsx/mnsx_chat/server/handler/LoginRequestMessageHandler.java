package top.mnsx.mnsx_chat.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import top.mnsx.mnsx_chat.message.LoginRequestMessage;
import top.mnsx.mnsx_chat.message.LoginResponseMessage;
import top.mnsx.mnsx_chat.server.service.UserServiceFactory;
import top.mnsx.mnsx_chat.server.session.SessionFactory;

/**
 * @BelongsProject: mnsx_chat
 * @User: Mnsx_x
 * @CreateTime: 2022/11/20 9:38
 * @Description:
 */
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String password = msg.getPassword();
        LoginResponseMessage message = null;
        boolean ifSuccess = UserServiceFactory.getUserService()
                .login(username, password);
        if (ifSuccess) {
            SessionFactory.getSession().bind(ctx.channel(), username);
            message = new LoginResponseMessage(true, "登录成功");
        } else {
            message = new LoginResponseMessage(false, "账号或者密码错误");
        }
        ctx.writeAndFlush(message);
    }
}
