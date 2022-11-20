package top.mnsx.mnsx_chat.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import top.mnsx.mnsx_chat.server.session.SessionFactory;

/**
 * @BelongsProject: mnsx_chat
 * @User: Mnsx_x
 * @CreateTime: 2022/11/20 11:47
 * @Description:
 */
@ChannelHandler.Sharable
public class QuitHandler extends ChannelInboundHandlerAdapter {
    // 当连接断开时，触发
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionFactory.getSession().unbind(ctx.channel());
        System.out.println(ctx.channel() + "已经退出");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        SessionFactory.getSession().unbind(ctx.channel());
        System.out.println(ctx.channel() + "异常退出");
    }
}
