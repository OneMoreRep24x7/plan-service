package com.ashish.planservice.configuration;

import com.ashish.planservice.dto.MsgDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service",url ="http://65.1.243.55:8081")
public interface NotificationProxy {
    @PostMapping("/sendNotification")
    public void sendNotification(@RequestBody MsgDTO message);
}
