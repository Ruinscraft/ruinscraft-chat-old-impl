package com.ruinscraft.chat.channels;

import java.util.HashMap;
import java.util.Map;

import com.ruinscraft.chat.ChatPlugin;
import com.ruinscraft.chat.channels.types.GlobalChatChannel;
import com.ruinscraft.chat.channels.types.LocalChatChannel;
import com.ruinscraft.chat.channels.types.MBChatChannel;
import com.ruinscraft.chat.channels.types.local.HubLocalChatChannel;
import com.ruinscraft.chat.channels.types.local.InfiniteLocalChatChannel;
import com.ruinscraft.chat.channels.types.local.PlotsLocalChatChannel;
import com.ruinscraft.chat.channels.types.local.SkyblockLocalChatChannel;

public class ChatChannelHandler {

	private static ChatPlugin chatPlugin = ChatPlugin.getInstance();
	
	private Map<String, ChatChannel> chatChannels = new HashMap<>();
	
	public ChatChannelHandler(String localVariant) {
		chatChannels.put("global", new GlobalChatChannel(chatPlugin.getConfig().getString("channels.global.format")));
		
		Map<String, LocalChatChannel> localChatChannels = new HashMap<>();
		
		String localFormat = chatPlugin.getConfig().getString("channels.local.format");
		
		localChatChannels.put("hub", new HubLocalChatChannel(localFormat));
		localChatChannels.put("infinite", new InfiniteLocalChatChannel(localFormat));
		localChatChannels.put("plots", new PlotsLocalChatChannel(localFormat));
		localChatChannels.put("skyblock", new SkyblockLocalChatChannel(localFormat));
		
		if (localChatChannels.containsKey(localVariant)) {
			chatChannels.put("local", localChatChannels.get(localVariant));
		}
		
		chatChannels.put("mb", new MBChatChannel(chatPlugin.getConfig().getString("channels.mb.format")));
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
