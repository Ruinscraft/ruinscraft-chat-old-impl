package com.ruinscraft.chat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ruinscraft.chat.ChatPlugin;
import com.ruinscraft.chat.players.ChatPlayer;

public class PMCommand implements CommandExecutor {

	private static ChatPlugin chatPlugin = ChatPlugin.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}

		Player player = (Player) sender;
		
		ChatPlayer chatPlayer = chatPlugin.getChatPlayerHandler().getChatPlayer(player.getUniqueId());
		
		
		
		
		
		
		return true;
	}
	
}
