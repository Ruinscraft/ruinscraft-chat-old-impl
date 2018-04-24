package com.ruinscraft.chat.redis;

import org.bukkit.Bukkit;

import com.ruinscraft.chat.ChatPlugin;

import net.md_5.bungee.api.ChatColor;

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

	public String getFormatted(String format) {
		return ChatColor.translateAlternateColorCodes('&',
				format
				.replace("%server%", serverName)
				.replace("%channel%", channelName)
				.replace("%prefix%", ChatPlugin.getInstance().getChat().getPlayerPrefix(Bukkit.getServer().getWorlds().get(0), sender))
				.replace("%sender%", sender)
				.replace("%message%", payload));
	}

	public String getAsJson() {
		return ChatPlugin.GSON.toJson(this);
	}

	public static ChatMessage fromJson(String json) {
		return ChatPlugin.GSON.fromJson(json, ChatMessage.class);
	}

}
