package com.gizmoduck.chatapp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.gizmoduck.chatapp.model.ChatMessage;

/**
 * @author achark
 *
 *all the messages sent from clients with a destination starting with "/app" will be routed to
 * these message handling methods annotated with @MessageMapping.
 * 
 * For example, a message with destination /app/chat.sendMessage will be routed to the sendMessage() method, 
 * and a message with destination /app/chat.addUser will be routed to the addUser() method.


 */
@Controller
public class ChatappController {

	@MessageMapping("chat.addUser")
	@SendTo("/topic/public")
	public ChatMessage addUser(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
		//Add username in websocket session
		headerAccessor.getSessionAttributes().put("username", message.getSender());
		return message;
	}
	
	@MessageMapping("chat.sendMessage")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage message) {
		return message;
	}
}
