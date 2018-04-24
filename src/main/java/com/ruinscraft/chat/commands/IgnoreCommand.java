package com.ruinscraft.chat.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ruinscraft.chat.ChatPlugin;
import com.ruinscraft.chat.players.ChatPlayer;

public class IgnoreCommand implements CommandExecutor {

	private static ChatPlugin chatPlugin = ChatPlugin.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}

		Player player = (Player) sender;
		
		ChatPlayer chatPlayer = chatPlugin.getChatPlayerHandler().getChatPlayer(player.getUniqueId());

		if (args.length > 1) {
			try {
				OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
				
				chatPlayer.ignore(target.getUniqueId());
				
				player.sendMessage("Ignored " + target.getName());
				
				return true;
			} catch (NullPointerException e) {
				player.sendMessage("Target not found");
				
				return false;
			}
		}

		player.sendMessage("Ignoring:");
		
		chatPlayer.getIgnoring().forEach(ignored -> player.sendMessage(Bukkit.getOfflinePlayer(ignored).getName()));
		
		return true;
	}
	
}
