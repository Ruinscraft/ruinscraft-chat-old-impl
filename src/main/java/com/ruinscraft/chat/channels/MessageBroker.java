package com.ruinscraft.chat.channels;

import com.ruinscraft.chat.redis.ChatMessage;

public interface MessageBroker {

	void onChat(ChatMessage chatMessage);
	
	void sendChat(ChatMessage chatMessage);
	
	void sendPM(ChatMessage chatMessage);
	
	void onPM(ChatMessage chatMessage);
	
}
