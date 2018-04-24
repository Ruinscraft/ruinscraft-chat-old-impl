package com.ruinscraft.chat.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MySqlStorage implements SqlStorage {

	private DataSource dataSource;
	
	private String query_ignored, query_muted, query_spying, query_focused, update_focused, insert_ignored, insert_muted, insert_spying, 
	delete_ignored, delete_muted, delete_spying;
	
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
		
		createTables();
		
		query_ignored = "SELECT ignored FROM chat_ignored_players WHERE uuid = ?;";
		query_muted = "SELECT channel FROM chat_muted_players WHERE uuid = ?;";
		query_spying = "SELECT channel FROM chat_spying_channels WHERE uuid = ?;";
		query_focused = "SELECT channel FROM chat_focused_channel WHERE uuid = ?;";
		
		update_focused = "INSERT INTO chat_focused_channel (uuid, channel) VALUES (?, ?) ON DUPLICATE KEY UPDATE uuid = uuid, channel = channel;";
		
		insert_ignored = "INSERT INTO chat_ignored_players (ignorer, ignored) VALUES (?, ?);";
		insert_muted = "INSERT INTO chat_muted_channels (uuid, channel) VALUES (?, ?);";
		insert_spying = "INSERT INTO chat_spying_channels (uuid, channel) VALUES (?, ?);";
		
		delete_ignored = "DELETE FROM chat_ignored_players WHERE ignorer = ? AND ignored = ?;";
		delete_muted = "DELETE FROM chat_muted_channels WHERE uuid = ? AND channel = ?;";
		delete_spying = "DELETE FROM chat_spying_channels WHERE uuid = ? AND channel = ?;";
	}


	@Override
	public void createTables() {
		try (Connection c = getConnection()) {
			String sql = new String(
					Files.readAllBytes(Paths.get(getClass().getResource("mysql_tables.sql").getFile())));
			
			try (PreparedStatement ps = c.prepareStatement(sql)) {
				ps.execute();
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}


	@Override
	public Set<UUID> getIgnored(UUID uuid) {
		Set<UUID> ignored = new HashSet<>();

		try (Connection c = getConnection();
				PreparedStatement ps = c.prepareStatement(query_ignored)) {
			ps.setString(1, uuid.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ignored.add(UUID.fromString(rs.getString("ignored")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ignored;
	}


	@Override
	public Set<String> getMuted(UUID uuid) {
		Set<String> muted = new HashSet<>();

		try (Connection c = getConnection();
				PreparedStatement ps = c.prepareStatement(query_muted)) {
			ps.setString(1, uuid.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				muted.add(rs.getString("muted"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return muted;
	}


	@Override
	public Set<String> getSpying(UUID uuid) {
		Set<String> spying = new HashSet<>();

		try (Connection c = getConnection();
				PreparedStatement ps = c.prepareStatement(query_spying)) {
			ps.setString(1, uuid.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				spying.add(rs.getString("spying"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return spying;
	}


	@Override
	public String getFocusedChannel(UUID uuid) {
		String focused = "";

		try (Connection c = getConnection();
				PreparedStatement ps = c.prepareStatement(query_focused)) {
			ps.setString(1, uuid.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				focused = rs.getString("focused");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return focused;
	}


	@Override
	public void setFocusedChannel(UUID uuid, String channelName) {
		try (Connection c = getConnection();
				PreparedStatement ps = c.prepareStatement(update_focused)) {
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void addIgnored(UUID ignorer, UUID ignored) {
		try (Connection c = getConnection();
				PreparedStatement ps = c.prepareStatement(insert_ignored)) {
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void addMuted(UUID uuid, String channelName) {
		try (Connection c = getConnection();
				PreparedStatement ps = c.prepareStatement(insert_muted)) {
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void addSpying(UUID uuid, String channelName) {
		try (Connection c = getConnection();
				PreparedStatement ps = c.prepareStatement(insert_spying)) {
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void removeIgnored(UUID ignorer, UUID ignored) {
		try (Connection c = getConnection();
				PreparedStatement ps = c.prepareStatement(delete_ignored)) {
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void removeMuted(UUID uuid, String channelName) {
		try (Connection c = getConnection();
				PreparedStatement ps = c.prepareStatement(delete_muted)) {
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void removeSpying(UUID uuid, String channelName) {
		try (Connection c = getConnection();
				PreparedStatement ps = c.prepareStatement(delete_spying)) {
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
