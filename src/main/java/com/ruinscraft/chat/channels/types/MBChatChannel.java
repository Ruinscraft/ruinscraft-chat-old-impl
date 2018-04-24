package com.ruinscraft.chat.channels.types;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ruinscraft.chat.channels.ChatChannel;

public class MBChatChannel extends ChatChannel {

	public MBChatChannel(String format) {
		super("mb", format, false, false, Optional.of("chat.channel.mb"));
	}

	@Override
	public Set<Player> getRecipients(Optional<Player> sender) {
		Set<Player> recipients = new HashSet<>();
		
		Bukkit.getOnlinePlayers().forEach(p -> {
			if (p.hasPermission(super.getPermission().get())) {
				recipients.add(p);
			}
		});
		
		return recipients;
	}
	
}
