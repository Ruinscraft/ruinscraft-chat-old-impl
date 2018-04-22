package com.ruinscraft.chat.redis;

import com.ruinscraft.chat.ChatPlugin;

public class ChatMessage {

	private String serverName;
	private String channelName;
	private String sender;
	private String payload;
	
	public ChatMessage(String serverName, String channelName, String sender, String payload) {
		this.serverName = serverName;
		this.channelName = channelName;
		this.sender = sender;
		this.payload = payload;
	}

	public String getServerName() {
		return serverName;
	}

	public String getChannelName() {
		return channelName;
	}

	public String getSender() {
		return sender;
	}

	public String getPayload() {
		return payload;
	}
	
	public String getAsJson() {
		return ChatPlugin.GSON.toJson(this);
	}

	public static ChatMessage fromJson(String json) {
		return ChatPlugin.GSON.fromJson(json, ChatMessage.class);
	}
	
}
