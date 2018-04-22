package com.ruinscraft.chat.storage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import java.util.UUID;

import javax.sql.DataSource;

import com.ruinscraft.chat.channels.ChatChannel;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MySqlStorage implements SqlStorage {

	private DataSource dataSource;
	
	public MySqlStorage(String host, int port, String database, String username, String password) {
		HikariConfig hikariConfig = new HikariConfig();

		hikariConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
		hikariConfig.setUsername(username);
		hikariConfig.setPassword(password);
		hikariConfig.setPoolName("ruinscraft-chat-pool");
		hikariConfig.setMaximumPoolSize(3);
		hikariConfig.setConnectionTimeout(3000);
		hikariConfig.setLeakDetectionThreshold(3000);

		dataSource = new HikariDataSource(hikariConfig);
	}
	
	
	@Override
	public void createTables() {
		
		
		
	}

	@Override
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}


	@Override
	public Set<UUID> getIgnored(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Set<String> getMuted(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Set<String> getSpying(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ChatChannel getFocusedChannel(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setFocusedChannel(UUID uuid, String channelName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addIgnored(UUID ignorer, UUID ignored) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addMuted(UUID uuid, String channelName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addSpying(UUID uuid, String channelName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeIgnored(UUID ignorer, UUID ignored) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeMuted(UUID uuid, String channelName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeSpying(UUID uuid, String channelName) {
		// TODO Auto-generated method stub
		
	}
	
}
