-- Table for Object
CREATE TABLE Object (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    x REAL NOT NULL,
    y REAL NOT NULL,
    last_x REAL NOT NULL,
    last_y REAL NOT NULL,
    or_x INTEGER NOT NULL,
    or_y INTEGER NOT NULL,
    speed_x REAL NOT NULL,
    speed_y REAL NOT NULL,
    ax REAL NOT NULL,
    ay REAL NOT NULL,
    mass REAL NOT NULL,
    friction REAL NOT NULL
);

-- Table for Character
CREATE TABLE Character (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    object_id INTEGER NOT NULL,
    class TEXT NOT NULL,
    pv INTEGER NOT NULL,
    pv_max INTEGER NOT NULL,
    defense INTEGER NOT NULL,
    power INTEGER NOT NULL,
    range INTEGER NOT NULL,
    name TEXT NOT NULL,
    attack_charged BOOLEAN NOT NULL DEFAULT 0,
    attack_timer REAL NOT NULL DEFAULT 0.0,
    attack_cooldown REAL NOT NULL DEFAULT 1.0,
    FOREIGN KEY (object_id) REFERENCES Object(id)
);

-- Table for Ennemy
CREATE TABLE Ennemie (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    character_id INTEGER NOT NULL,
    tiled_map TEXT,
    weapon TEXT,
    detection_range INTEGER NOT NULL,
    FOREIGN KEY (character_id) REFERENCES Character(id)
);

-- Table for Hero
CREATE TABLE Hero (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    character_id INTEGER NOT NULL,
    FOREIGN KEY (character_id) REFERENCES Character(id)
);

-- Table for Boss
CREATE TABLE Boss (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    ennemie_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    detection_range INTEGER NOT NULL,
    FOREIGN KEY (ennemie_id) REFERENCES Ennemie(id)
);
