package com.ruinscraft.chat.storage;

import java.util.Set;
import java.util.UUID;

public interface Storage {

	Set<UUID> getIgnored(UUID uuid);
	
	Set<String> getMuted(UUID uuid);
	
	Set<String> getSpying(UUID uuid);
	
	String getFocusedChannel(UUID uuid);
	
	void setFocusedChannel(UUID uuid, String channelName);
	
	void addIgnored(UUID ignorer, UUID ignored);
	
	void addMuted(UUID uuid, String channelName);
	
	void addSpying(UUID uuid, String channelName);
	
	void removeIgnored(UUID ignorer, UUID ignored);
	
	void removeMuted(UUID uuid, String channelName);
	
	void removeSpying(UUID uuid, String channelName);
	
}
