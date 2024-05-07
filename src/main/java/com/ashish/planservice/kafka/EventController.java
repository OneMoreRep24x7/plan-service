package com.ashish.planservice.kafka;




import com.ashish.planservice.configuration.NotificationProxy;
import com.ashish.planservice.dto.MsgDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producer-app")
public class EventController {

    @Autowired
    private KafkaMessagePublisher publisher;
    @Autowired
    private NotificationProxy notificationProxy;

//    @GetMapping("/publish/{message}")
//    public ResponseEntity<?> publishMessage(@PathVariable String message) {
//        try {
//            for (int i = 0; i <= 100000; i++) {
//                publisher.sendMessageToTopic(message + " : " + i);
//            }
//            return ResponseEntity.ok("message published successfully ..");
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .build();
//        }
//    }

    @PostMapping("/publish")
    public void sendEvents(@RequestBody MsgDTO customer) {
//        publisher.sendEventsToTopic(customer);
        notificationProxy.sendNotification(customer);
    }


}
