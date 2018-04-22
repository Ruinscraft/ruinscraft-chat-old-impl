package com.ruinscraft.chat.channels.types;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.ruinscraft.chat.channels.ChatChannel;

public abstract class LocalChatChannel extends ChatChannel {

	private String variant;
	
	public LocalChatChannel(String variant) {
		super("local", true, true);
		this.variant = variant;
	}
	
	public String getVariant() {
		return variant;
	}
	
	public Set<Player> getPlayersInRad(Player reference, int rad) {
		Set<Player> players = new HashSet<>();
		
		final Location referenceLoc = reference.getLocation();
		
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if (onlinePlayer == null || !onlinePlayer.isOnline()) {
				continue;
			}
			
			if (onlinePlayer.getLocation().distance(referenceLoc) < rad) {
				players.add(onlinePlayer);
			}
		}

		return players;
	}
	
}
