package top.mnsx.mnsx_chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.mnsx.mnsx_chat.server.ChatServer;

@SpringBootApplication
public class MnsxChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(MnsxChatApplication.class, args);
        ChatServer.start();
    }

}
