package com.ruinscraft.chat.channels.types.local;

import java.util.Optional;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Sets;
import com.ruinscraft.chat.channels.types.LocalChatChannel;

public class HubLocalChatChannel extends LocalChatChannel {

	public HubLocalChatChannel(String format) {
		super("hub", format);
	}

	@Override
	public Set<Player> getRecipients(Optional<Player> sender) {
		return Sets.newHashSet(Bukkit.getOnlinePlayers());
	}

}
