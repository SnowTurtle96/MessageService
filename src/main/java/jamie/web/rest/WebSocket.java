package jamie.web.rest;

import jamie.domain.UserMessagesSent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocket {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(UserMessagesSent message) throws Exception {
        String a = message.toString();
        System.out.println(a);
        return new String(a);
    }

}
