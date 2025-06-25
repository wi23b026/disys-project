CREATE TABLE IF NOT EXISTS energy_data (
    date TIMESTAMP PRIMARY KEY,
    current_energy REAL NOT NULL,
    grid_portion REAL NOT NULL,
    community_produced REAL NOT NULL,
    community_used REAL NOT NULL,
    grid_used REAL NOT NULL
);