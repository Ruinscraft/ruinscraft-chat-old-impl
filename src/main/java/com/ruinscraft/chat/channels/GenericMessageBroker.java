package com.ruinscraft.chat.channels;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;
import com.ruinscraft.chat.ChatPlugin;
import com.ruinscraft.chat.redis.ChatMessage;
import com.ruinscraft.chat.redis.RedisHandler;

import net.md_5.bungee.api.ChatColor;

public class GenericMessageBroker implements MessageBroker {

	private static ChatPlugin chatPlugin = ChatPlugin.getInstance();
	
	@Override
	public void onChat(ChatMessage chatMessage) {
		Preconditions.checkNotNull(chatMessage, "ChatMessage cannot be null");
		
		ChatChannel chatChannel = chatPlugin.getChatChannelHandler().getChannel(chatMessage.getChannelName());
		
		boolean permissionRequired = chatChannel.getPermission().isPresent();
		
		Optional<Player> senderOptional = Optional.empty();
		
		if (Bukkit.getPlayer(chatMessage.getSender()) != null && Bukkit.getPlayer(chatMessage.getSender()).isOnline()) {
			senderOptional = Optional.of(Bukkit.getPlayer(chatMessage.getSender()));
		}

		for (Player recipient : chatChannel.getRecipients(senderOptional)) {
			// if this isnt the server the message was sent on and the channel is local
			if (chatMessage.getChannelName().equals("local") && !chatMessage.getServerName().equals(ChatPlugin.getServerName())) {
				break;
			}
			
			if (recipient == null || !recipient.isOnline()) {
				continue;
			}

			if (permissionRequired) {
				if (!recipient.hasPermission(chatChannel.getPermission().get())) {
					continue;
				}
			}
			
			recipient.sendMessage(chatMessage.getFormatted(chatChannel.getFormat()));
		}
		
		System.out.println(ChatColor.stripColor(chatMessage.getFormatted(chatChannel.getFormat())));
	}

	@Override
	public void sendChat(ChatMessage chatMessage) {
		Preconditions.checkNotNull(chatMessage, "ChatMessage cannot be null");
		RedisHandler redisHandler = chatPlugin.getRedisHandler();
		redisHandler.sendMessage(chatMessage.getAsJson());	
	}

	@Override
	public void onPM(ChatMessage chatMessage) {
		
	}

	@Override
	public void sendPM(ChatMessage chatMessage) {
	
	}
	
}
