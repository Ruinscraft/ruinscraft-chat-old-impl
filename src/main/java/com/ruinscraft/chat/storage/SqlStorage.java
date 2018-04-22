package com.ruinscraft.chat.storage;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlStorage extends Storage {

	void createTables();

	Connection getConnection() throws SQLException;

}
