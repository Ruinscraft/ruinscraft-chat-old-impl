package com.ruinscraft.chat.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.ruinscraft.chat.ChatPlugin;

public class JoinListener implements Listener {

	private static ChatPlugin chatPlugin = ChatPlugin.getInstance();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		
		chatPlugin.getChatPlayerHandler().getChatPlayer(player.getUniqueId());
		
		if (!ChatPlugin.isServerNameSet()) {
			chatPlugin.getServer().getScheduler().runTaskLaterAsynchronously(ChatPlugin.getInstance(), () -> {
				if (player != null && player.isOnline()) {
					ByteArrayDataOutput out = ByteStreams.newDataOutput();
					out.writeUTF("GetServer");
					player.sendPluginMessage(ChatPlugin.getInstance(), "BungeeCord", out.toByteArray());
				}
			}, 20L);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		
	}

}
