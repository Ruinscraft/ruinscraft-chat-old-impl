package com.ruinscraft.chat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ruinscraft.chat.ChatPlugin;
import com.ruinscraft.chat.channels.ChatChannel;
import com.ruinscraft.chat.players.ChatPlayer;

import net.md_5.bungee.api.ChatColor;

public class ChannelCommand implements CommandExecutor {

	private static ChatPlugin chatPlugin = ChatPlugin.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}

		Player player = (Player) sender;

		ChatPlayer chatPlayer = chatPlugin.getChatPlayerHandler().getChatPlayer(player.getUniqueId());

		if (args.length > 0) {

			String channelName = args[0];

			ChatChannel newChannel = chatPlugin.getChatChannelHandler().getChannel(channelName);

			if (newChannel == null) {
				player.sendMessage("Channel does not exist");

				return true;
			}

			if (chatPlayer.setFocusedChannel(newChannel)) {
				player.sendMessage("Set focused channel to " + newChannel.getName());
			} else {
				player.sendMessage("You cannot set focus to this channel");
			}
			
			return true;
		} 

		player.sendMessage(ChatColor.YELLOW + "Available channels:");

		for (ChatChannel chatChannel : chatPlugin.getChatChannelHandler().getChatChannels().values()) {
			if (chatChannel.equals(chatPlayer.getFocusedChannel())) {
				player.sendMessage(ChatColor.GREEN + chatChannel.getName());
			} else {
				if (chatChannel.getPermission().isPresent()) {
					if (!player.hasPermission(chatChannel.getPermission().get())) {
						continue;
					}
				}

				if (chatPlayer.isMuted(chatChannel)) {
					player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + chatChannel.getName());
				} else {
					player.sendMessage(ChatColor.GRAY + chatChannel.getName());
				}
			}
		}

		return true;
	}

}
