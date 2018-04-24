package com.ruinscraft.chat;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.ruinscraft.chat.channels.ChatChannelHandler;
import com.ruinscraft.chat.channels.GenericMessageBroker;
import com.ruinscraft.chat.channels.MessageBroker;
import com.ruinscraft.chat.commands.ChannelCommand;
import com.ruinscraft.chat.commands.IgnoreCommand;
import com.ruinscraft.chat.commands.PMCommand;
import com.ruinscraft.chat.commands.SpyCommand;
import com.ruinscraft.chat.listeners.ChatListener;
import com.ruinscraft.chat.listeners.JoinListener;
import com.ruinscraft.chat.players.ChatPlayerHandler;
import com.ruinscraft.chat.redis.RedisHandler;
import com.ruinscraft.chat.storage.Storage;

import net.milkbowl.vault.chat.Chat;

public class ChatPlugin extends JavaPlugin implements PluginMessageListener {

	private Storage storage;
	private RedisHandler redisHandler;
	private MessageBroker messageBroker;
	private ChatPlayerHandler chatPlayerHandler;
	private ChatChannelHandler chatChannelHandler;

    private Chat vaultChat;
	
	private boolean usingPlotSquared = false;
	
	private static Optional<String> bungeeServerName = Optional.empty();

	public static void setServerName(String serverName) {
		Preconditions.checkNotNull(serverName, "serverName cannot be null");
		bungeeServerName = Optional.of(serverName);
	}

	public static String getServerName() {
		return bungeeServerName.orElse(ChatPlugin.getInstance().getConfig().getString("channels.local.variant", "?"));
	}
	
	public static boolean isServerNameSet() {
		return bungeeServerName.isPresent();
	}

	private static ChatPlugin instance;

	public static ChatPlugin getInstance() {
		return instance;
	}
	
	public static final Gson GSON = new Gson();

	@Override
	public void onEnable() {
		instance = this;

		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			getLogger().info("Vault not found.");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		saveDefaultConfig();

		//storage = new MySqlStorage(
		//		getConfig().getString("storage.mysql.host"),
		//		getConfig().getInt("storage.mysql.port"),
		//		getConfig().getString("storage.mysql.username"),
		//		getConfig().getString("storage.mysql.password"),
		//		getConfig().getString("storage.mysql.database"));

		messageBroker = new GenericMessageBroker();
		chatPlayerHandler = new ChatPlayerHandler();
		chatChannelHandler = new ChatChannelHandler(getConfig().getString("channels.local.variant"));

		redisHandler = new RedisHandler(
				getConfig().getString("redis.host", "localhost"),
				getConfig().getString("redis.channel", "rcdev"));

		getServer().getPluginManager().registerEvents(new JoinListener(), this);
		getServer().getPluginManager().registerEvents(new ChatListener(), this);

		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

		getCommand("channel").setExecutor(new ChannelCommand());
		getCommand("ignore").setExecutor(new IgnoreCommand());
		getCommand("pm").setExecutor(new PMCommand());
		getCommand("spy").setExecutor(new SpyCommand());

		usingPlotSquared = getServer().getPluginManager().isPluginEnabled("PlotSquared");

		if (usingPlotSquared) {
			getLogger().info("Found PlotSquared");
		}
		
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        vaultChat = rsp.getProvider();
	}

	@Override
	public void onDisable() {
		instance = null;

		storage = null;

		redisHandler.shutdown();
		redisHandler = null;

		messageBroker = null;

		chatPlayerHandler.clearCache();
		chatPlayerHandler = null;

		chatChannelHandler.clearChannels();
		chatChannelHandler = null;
	}

	public Storage getStorage() {
		return storage;
	}

	public RedisHandler getRedisHandler() {
		return redisHandler;
	}

	public MessageBroker getMessageBroker() {
		return messageBroker;
	}

	public ChatPlayerHandler getChatPlayerHandler() {
		return chatPlayerHandler;	
	}

	public ChatChannelHandler getChatChannelHandler() {
		return chatChannelHandler;
	}

	public boolean isUsingPlotSquared() {
		return usingPlotSquared;
	}
	
	public Chat getVaultChat() {
		return vaultChat;
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord")) {
			return;
		}
		
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		
		String subChannel = in.readUTF();
		
		if (subChannel.equals("GetServer")){
			String name = in.readUTF();
			ChatPlugin.setServerName(name);
		}
	}

}
