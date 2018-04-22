package com.ruinscraft.chat.redis;

import com.ruinscraft.chat.ChatPlugin;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public class RedisHandler {

	private static ChatPlugin chatPlugin = ChatPlugin.getInstance();

	private JedisPool jedisPool;
	private String channel;

	private RedisChatSubscriber subscriber;

	public RedisHandler(String hostname, String channel) {
		jedisPool = new JedisPool(hostname);
		
		this.channel = channel;

		subscriber = new RedisChatSubscriber();

		chatPlugin.getServer().getScheduler().runTaskAsynchronously(ChatPlugin.getInstance(), () -> {
			try (Jedis jedis = jedisPool.getResource()) {
				jedis.subscribe(subscriber, channel);
			}
		});
	}

	public void shutdown() {
		subscriber.unsubscribe();
		jedisPool.close();
	}

	public void sendMessage(String message) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.publish(channel, message);
		}
	}

	private class RedisChatSubscriber extends JedisPubSub {

		@Override
		public void onMessage(String channel, String message) {
			if (!channel.equals(channel)) {
				return;
			}

			if (message == null) {
				return;
			}
			
			ChatMessage chatMessage = null;
			
			try {
				chatMessage = ChatMessage.fromJson(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			chatPlugin.getMessageBroker().onChat(chatMessage);
		}

	}

}
