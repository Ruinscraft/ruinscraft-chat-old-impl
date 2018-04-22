package com.ruinscraft.chat.channels.types.local;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.ruinscraft.chat.ChatPlugin;
import com.ruinscraft.chat.channels.types.LocalChatChannel;

public class InfiniteLocalChatChannel extends LocalChatChannel {

	private static ChatPlugin chatPlugin = ChatPlugin.getInstance();
	
	public InfiniteLocalChatChannel() {
		super("infinite");
	}

	@Override
	public Set<Player> getRecipients(Optional<Player> sender) {
		Preconditions.checkNotNull(sender, "sender cannot be null");
		
		Set<Player> recipients = new HashSet<>();
		
		if (!sender.isPresent()) {
			return recipients;
		}
		
		// get general nearby players
		recipients.addAll(super.getPlayersInRad(sender.get(), 50));
		
		if (chatPlugin.isUsingPlotSquared()) {
			// get players in plot
			PlotPlayer plotPlayer = PlotPlayer.wrap(sender.get());
			
			Plot plot = plotPlayer.getCurrentPlot();
			
			if (plot != null) {
				plot.getPlayersInPlot().forEach(pp -> recipients.add(Bukkit.getPlayer(pp.getUUID())));
			}
		}
		
		return recipients;
	}
	
}
