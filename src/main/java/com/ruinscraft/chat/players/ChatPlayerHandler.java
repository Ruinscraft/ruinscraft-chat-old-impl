package com.ruinscraft.chat.players;

import java.util.UUID;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class ChatPlayerHandler {

	private LoadingCache<UUID, ChatPlayer> cache;

	public ChatPlayerHandler() {
		cache = CacheBuilder
				.newBuilder()
				.build(new ChatPlayerCacheLoader());
	}
	
	public LoadingCache<UUID, ChatPlayer> getCache() {
		if (cache == null) {
			cache = CacheBuilder
					.newBuilder()
					.build(new ChatPlayerCacheLoader());
		}

		return cache;
	}
	
	public void clearCache() {
		cache.invalidateAll();
	}

	public ChatPlayer getChatPlayer(UUID uuid) {
		return getCache().getUnchecked(uuid);
	}

	private class ChatPlayerCacheLoader extends CacheLoader<UUID, ChatPlayer> {
		@Override
		public ChatPlayer load(UUID uuid) throws Exception {
			return new ChatPlayer(uuid);
		}
	}

}
