CREATE TABLE IF NOT EXISTS chat_focused_channel (uuid VARCHAR(36), channel VARCHAR(32), PRIMARY KEY (uuid));
CREATE TABLE IF NOT EXISTS chat_muted_channels (uuid VARCHAR(36), channel VARCHAR(32), UNIQUE (uuid, channel));
CREATE TABLE IF NOT EXISTS chat_spying_channels (uuid VARCHAR(36), channel VARCHAR(32), UNIQUE (uuid, channel));
CREATE TABLE IF NOT EXISTS chat_ignored_players (ignorer VARCHAR(36), ignored VARCHAR(36), UNIQUE (ignorer, ignored));