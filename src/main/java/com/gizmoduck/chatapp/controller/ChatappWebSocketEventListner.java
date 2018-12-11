package com.gizmoduck.chatapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import com.gizmoduck.chatapp.model.ChatMessage;
import com.gizmoduck.chatapp.model.MessageType;

/**
 * @author achark
 * 
 * use event listeners to listen for socket connect and disconnect events so 
 * that we can log these events and also broadcast them when a user joins or leaves the chat room
 *
 */
@Component
public class ChatappWebSocketEventListner {

	private static final Logger logger = LoggerFactory.getLogger(ChatappWebSocketEventListner.class);
	
	@Autowired
	private SimpMessageSendingOperations operations;
	
	@EventListener
	public void handleWebSocketConnectListner(SessionConnectedEvent event) {
		logger.info("New websocket connection received");
	}
	
	@EventListener
	public void handleWebSocketDisconnectListner(SessionConnectedEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String username = (String) headerAccessor.getSessionAttributes().get("username");
		
		if (username != null) {
			logger.info("User disconnected : "+ username);
			
			ChatMessage message = new ChatMessage();
			message.setSender(username);
			message.setType(MessageType.LEAVE);
			
			operations.convertAndSend("/topic/public", message);
		}
	}
}
