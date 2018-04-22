package com.ruinscraft.chat.channels.types;

import java.util.Optional;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Sets;
import com.ruinscraft.chat.channels.ChatChannel;

public class GlobalChatChannel extends ChatChannel {

	public GlobalChatChannel(String format) {
		super("global", format, false, true);
	}

	@Override
	public Set<Player> getRecipients(Optional<Player> sender) {
		return Sets.newHashSet(Bukkit.getOnlinePlayers());
	}
	
}
