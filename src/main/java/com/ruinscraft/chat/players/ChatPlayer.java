package com.ruinscraft.chat.players;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.ruinscraft.chat.ChatPlugin;
import com.ruinscraft.chat.channels.ChatChannel;

public class ChatPlayer {

	private UUID uuid;
	private ChatChannel focusedChannel;
	private Set<UUID> ignoring = new HashSet<>();
	private Set<ChatChannel> spying = new HashSet<>();
	private Set<ChatChannel> muted = new HashSet<>();

	public ChatPlayer(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getUUID() {
		return uuid;
	}

	public ChatChannel getFocusedChannel() {
		if (focusedChannel == null) {
			return ChatPlugin.getInstance().getChatChannelHandler().getDefaultChannel();
		}

		return focusedChannel;
	}

	public boolean setFocusedChannel(ChatChannel focusedChannel) {
		if (!focusedChannel.isFocusable()) {
			return false;
		}
		
		this.focusedChannel = focusedChannel;
		
		return true;
	}

	public Set<UUID> getIgnoring() {
		return ignoring;
	}
	
	public boolean isIgnoring(UUID uuid) {
		return ignoring.contains(uuid);
	}
	
	public boolean ignore(UUID uuid) {
		return ignoring.add(uuid);
	}

	public Set<ChatChannel> getSpying() {
		return spying;
	}

	public Set<ChatChannel> getMuted() {
		return muted;
	}

	public boolean isMuted(ChatChannel chatChannel) {
		return muted.contains(chatChannel);
	}

}
