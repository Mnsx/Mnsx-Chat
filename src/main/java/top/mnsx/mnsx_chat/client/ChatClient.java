package top.mnsx.mnsx_chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import top.mnsx.mnsx_chat.message.*;
import top.mnsx.mnsx_chat.protocol.MessageCodecSharable;
import top.mnsx.mnsx_chat.protocol.ProtocolFrameDecoder;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class ChatClient {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        // 日志处理器
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        // 消息协议处理器
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        // 倒计时锁
        CountDownLatch waitForLogin = new CountDownLatch(1);
        // 登陆状态
        AtomicBoolean loginStatus = new AtomicBoolean();
        // 输入
        Scanner scanner = new Scanner(System.in);
        try {
            Channel channel = new Bootstrap()
                    .channel(NioSocketChannel.class)
                    .group(group)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 添加LengthFieldBasedFrameDecoder处理器
                            ch.pipeline().addLast(new ProtocolFrameDecoder());
//                            ch.pipeline().addLast(LOGGING_HANDLER);
                            ch.pipeline().addLast(MESSAGE_CODEC);
                            // 用来判断是不是，读写空闲时间过长
                            // 5s内没有发出channel消息，触发一个事件，WRITE_IDLE
                            ch.pipeline().addLast(new IdleStateHandler(0, 3, 0));
                            // ChannelDuplexHandler，可以同时作为出栈和入栈处理器
                            ch.pipeline().addLast(new ChannelDuplexHandler() {
                                // 用来触发特殊事件
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                    IdleStateEvent event = (IdleStateEvent) evt;
                                    // 触发了读空闲
                                    if (event.state() == IdleState.WRITER_IDLE) {
                                        ctx.writeAndFlush(new PingMessage());
                                    }
                                    super.userEventTriggered(ctx, evt);
                                }
                            });
                            ch.pipeline().addLast("client handler", new ChannelInboundHandlerAdapter() {
                                // 接收响应消息
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    System.out.println("----response\t" + msg);
                                    // 登录后响应
                                    if (msg instanceof LoginResponseMessage) {
                                        LoginResponseMessage response = (LoginResponseMessage) msg;
                                        // 判断是否登录成功j
                                        if (response.isSuccess()) {
                                            loginStatus.set(true);
                                        }
                                        waitForLogin.countDown();
                                    }
                                }

                                // 当连接成功时，提示用户登录
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    // 防止阻塞，开启新线程处理输入
                                    new Thread(() -> {
                                        System.out.println("请输入用户名：");
                                        String username = scanner.nextLine();
                                        System.out.println("请输入密码：");
                                        String password = scanner.nextLine();
                                        // 构建消息
                                        LoginRequestMessage message = new LoginRequestMessage(username, password);
                                        // 发送消息
                                        ctx.writeAndFlush(message);

                                        System.out.println("等待后续操作...");
                                        try {
                                            waitForLogin.await();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        // 登录失败
                                        if (!loginStatus.get()) {
                                            ctx.channel().close();
                                            return;
                                        }
                                        while (true) {
                                            System.out.println("-----------------------------------------");
                                            System.out.println("send [username] [content]");
                                            System.out.println("gSend [groupName] [content]");
                                            System.out.println("gCreate [groupName] [m1,m2,m3...]");
                                            System.out.println("gMembers [groupName]");
                                            System.out.println("gJoin [grouName]");
                                            System.out.println("gQuit [groupName]");
                                            System.out.println("quit");
                                            System.out.println("-----------------------------------------");
                                            System.out.println("请输入你的选择：");
                                            String command = scanner.nextLine();
                                            String[] arr = command.split(" ");
                                            switch (arr[0]) {
                                                case "send":
                                                    ctx.writeAndFlush(new ChatRequestMessage(username, arr[1], arr[2]));
                                                    break;
                                                case "gSend":
                                                    System.out.println("do fun");
                                                    ctx.writeAndFlush(new GroupChatRequestMessage(username, arr[1], arr[2]));
                                                    break;
                                                case "gCreate":
                                                    Set<String> members = new HashSet<>(
                                                            Arrays.asList(arr[2].split(","))
                                                    );
                                                    ctx.writeAndFlush(new GroupCreateRequestMessage(arr[1], members));
                                                    break;
                                                case "gMembers":
                                                    ctx.writeAndFlush(new GroupMembersRequestMessage(arr[1]));
                                                    break;
                                                case "gJoin":
                                                    ctx.writeAndFlush(new GroupJoinRequestMessage(username, arr[1]));
                                                    break;
                                                case "gQuit":
                                                    ctx.writeAndFlush(new GroupQuitRequestMessage(username, arr[1]));
                                                    break;
                                                case "quit":
                                                    ctx.channel().close();
                                                    return;
                                            }
                                        }
                                    }, "system in").start();
                                }
                            });
                        }
                    })
                    .connect("mnsx.top", 8080).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
