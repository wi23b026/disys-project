CREATE TABLE IF NOT EXISTS energy_data (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    current_energy REAL NOT NULL,
    grid_portion REAL NOT NULL,
    community_produced REAL NOT NULL,
    community_used REAL NOT NULL,
    grid_used REAL NOT NULL
);