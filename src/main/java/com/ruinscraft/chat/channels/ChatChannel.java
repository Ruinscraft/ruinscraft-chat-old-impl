package com.ruinscraft.chat.channels;

import java.util.Optional;
import java.util.Set;

import org.bukkit.entity.Player;

public abstract class ChatChannel {

	private String name;
	private String format;
	private boolean spyable;
	private boolean focusable;
	private Optional<String> permission = Optional.empty();
	
	public ChatChannel(String name, String format, boolean spyable, boolean focusable) {
		this.name = name;
		this.format = format;
		this.spyable = spyable;
		this.focusable = focusable;
	}

	public ChatChannel(String name, String format, boolean spyable, boolean focusable, Optional<String> permission) {
		this(name, format, spyable, focusable);
		this.permission = permission;
	}
	
	public String getName() {
		return name;
	}
	
	public String getFormat() {
		return format;
	}
	
	public boolean isSpyable() {
		return spyable;
	}
	
	public boolean isFocusable() {
		return focusable;
	}
	
	public Optional<String> getPermission() {
		return permission;
	}
	
	public abstract Set<Player> getRecipients(Optional<Player> sender);
	
}
