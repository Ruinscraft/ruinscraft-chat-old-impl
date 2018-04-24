package com.ruinscraft.chat.channels.types.local;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;
import com.ruinscraft.chat.channels.types.LocalChatChannel;

public class SkyblockLocalChatChannel extends LocalChatChannel {

	public SkyblockLocalChatChannel(String format) {
		super(format, "skyblock");
	}

	@Override
	public Set<Player> getRecipients(Optional<Player> sender) {
		Preconditions.checkNotNull(sender, "sender cannot be null");
		
		Set<Player> recipients = new HashSet<>();
		
		if (!sender.isPresent()) {
			return recipients;
		}
		
		// get general nearby players
		recipients.addAll(super.getPlayersInRad(sender.get(), 250));
		
		return recipients;
	}
	
}
