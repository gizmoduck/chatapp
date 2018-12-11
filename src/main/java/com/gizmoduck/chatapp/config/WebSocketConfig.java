package com.gizmoduck.chatapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	/* (non-Javadoc)
	 * @see org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer#configureMessageBroker(org.springframework.messaging.simp.config.MessageBrokerRegistry)
	 * configuring a message broker that will be used to route messages from one client to another
	 * first line defines that the messages whose destination starts with “/app” should be routed to message-handling methods
	 * the second line defines that the messages whose destination starts with “/topic” should be routed to the message broker. 
	 * Message broker broadcasts messages to all the connected clients who are subscribed to a particular topic.
	 * 
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app");
		registry.enableSimpleBroker("/topic"); //enables simple in memory broker
	//   Use this for enabling a Full featured broker like RabbitMQ
        
      /*  registry.enableStompBrokerRelay("/topic")
                .setRelayHost("localhost")
                .setRelayPort(9999)
                .setClientLogin("guest")
                .setClientPasscode("guest");
      */  
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer#registerStompEndpoints(org.springframework.web.socket.config.annotation.StompEndpointRegistry)
	 * // we register a websocket endpoint that the clients will use to connect to our websocket server.
	 * SockJS is used to enable fallback options for browsers that don’t support websocket.
	 * STOMP stands for Simple Text Oriented Messaging Protocol
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").withSockJS(); 
	}
	
	

}
