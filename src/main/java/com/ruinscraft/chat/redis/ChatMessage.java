package com.ruinscraft.chat.redis;

import com.ruinscraft.chat.ChatPlugin;

import net.md_5.bungee.api.ChatColor;

public class ChatMessage {

	private String serverName;
	private String channelName;
	private String prefix;
	private String sender;
	private String payload;

	public ChatMessage(String serverName, String channelName, String prefix, String sender, String payload) {
		this.serverName = serverName;
		this.channelName = channelName;
		this.prefix = prefix;
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

	public String getFormatted(String format) {
		return ChatColor.translateAlternateColorCodes('&', format
				.replace("%server%", serverName)
				.replace("%servercolor%", getServerColor().toString())
				.replace("%channel%", channelName)
				.replace("%prefix%", prefix)
				.replace("%sender%", sender)
				.replace("%message%", payload));
	}

	private ChatColor getServerColor() {
		if (serverName.equals(ChatPlugin.getServerName())) {
			return ChatColor.GREEN;
		}
		return ChatColor.GRAY;
	}
	
	public String getAsJson() {
		return ChatPlugin.GSON.toJson(this);
	}

	public static ChatMessage fromJson(String json) {
		return ChatPlugin.GSON.fromJson(json, ChatMessage.class);
	}

}
