package com.ruinscraft.chat.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.ruinscraft.chat.ChatPlugin;
import com.ruinscraft.chat.channels.ChatChannel;
import com.ruinscraft.chat.players.ChatPlayer;
import com.ruinscraft.chat.redis.ChatMessage;

public class ChatListener implements Listener {

	private static ChatPlugin chatPlugin = ChatPlugin.getInstance();
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		
		final Player player = event.getPlayer();
		final String payload = event.getMessage();
		
		ChatPlayer chatPlayer = chatPlugin.getChatPlayerHandler().getChatPlayer(player.getUniqueId());
		
		ChatChannel chatChannel = chatPlayer.getFocusedChannel();
		
		ChatMessage chatMessage = new ChatMessage(
				ChatPlugin.getServerName(), 
				chatChannel.getName(), 
				ChatPlugin.getInstance().getVaultChat().getPlayerPrefix(player), 
				player.getName(), payload);
		
		chatPlugin.getMessageBroker().sendChat(chatMessage);
 	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		final Player player = event.getPlayer();
		final String command = event.getMessage();
		
		for (ChatChannel chatChannel : chatPlugin.getChatChannelHandler().getChatChannels().values()) {
			if (!chatChannel.isFocusable()) {
				continue;
			}
			
			if (command.equalsIgnoreCase(chatChannel.getName())) {
				player.performCommand("channel " + chatChannel.getName());
			}
		}
	}
	
}
