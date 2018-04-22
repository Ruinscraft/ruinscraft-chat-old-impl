package com.ruinscraft.chat.channels;

import java.util.HashMap;
import java.util.Map;

import com.ruinscraft.chat.channels.types.GlobalChatChannel;
import com.ruinscraft.chat.channels.types.LocalChatChannel;
import com.ruinscraft.chat.channels.types.local.HubLocalChatChannel;
import com.ruinscraft.chat.channels.types.local.InfiniteLocalChatChannel;
import com.ruinscraft.chat.channels.types.local.PlotsLocalChatChannel;
import com.ruinscraft.chat.channels.types.local.SkyblockLocalChatChannel;

public class ChatChannelHandler {

	private Map<String, ChatChannel> chatChannels = new HashMap<>();
	
	public ChatChannelHandler(String localVariant) {
		chatChannels.put("global", new GlobalChatChannel());
		
		Map<String, LocalChatChannel> localChatChannels = new HashMap<>();
		
		localChatChannels.put("hub", new HubLocalChatChannel());
		localChatChannels.put("infinite", new InfiniteLocalChatChannel());
		localChatChannels.put("plots", new PlotsLocalChatChannel());
		localChatChannels.put("skyblock", new SkyblockLocalChatChannel());
		
		if (localChatChannels.containsKey(localVariant)) {
			chatChannels.put("local", localChatChannels.get(localVariant));
		}
	}
	
	public void clearChannels() {
		chatChannels.clear();
	}
	
	public Map<String, ChatChannel> getChatChannels() {
		return chatChannels;
	}
	
	public ChatChannel getChannel(String channelName) {
		return chatChannels.get(channelName);
	}
	
	public void addChannel(ChatChannel chatChannel) {
		chatChannels.put(chatChannel.getName(), chatChannel);
	}
	
	public void removeChannel(String channelName) {
		chatChannels.remove(channelName);
	}
	
	public ChatChannel getDefaultChannel() {
		return chatChannels.get("global"); 
	}
	
}
