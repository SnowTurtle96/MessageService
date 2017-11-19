package jamie.web.rest;

import jamie.domain.UserMessagesSent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebSocket {


    @MessageMapping("/chat.message")
    public String filterMessage(@Payload String message) {
        System.out.println("Message Detected");
        return message;
    }

}
