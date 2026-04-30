CREATE DATABASE NexusArena;
USE NexusArena;
-- TABLE: GAME
CREATE TABLE GAME (
    game_id INT PRIMARY KEY AUTO_INCREMENT,
    game_name VARCHAR(100) NOT NULL,
    publisher VARCHAR(100) NOT NULL,
    platform VARCHAR(50),
    genre VARCHAR(50));
-- TABLE: PLAYER
-- FIX:total_earnings removed(transitive dependency — 3NF violation).
-- (Derived Data)Computed on demand via vw_player_earnings.
CREATE TABLE PLAYER (
    player_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50)  NOT NULL UNIQUE,
    

real_name VARCHAR(100) NOT NULL,
    nationality VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    player_rank VARCHAR(30),
    date_of_birth DATE);
-- TABLE: TEAM
-- FIX 2: total_prize_money removed (transitive dependency — 3NF violation).
-- (Derived Data)Computed on demand via vw_team_earnings.
CREATE TABLE TEAM (
    team_id INT PRIMARY KEY AUTO_INCREMENT,
    team_name VARCHAR(100) NOT NULL UNIQUE,
    founded_date DATE,
    coach_name VARCHAR(100),
    country VARCHAR(50));

-- TABLE: ROSTER
-- FIX 3: UNIQUE changed from (player_id, team_id, game_id)
--        to (player_id, game_id) — a player may only be on
--        ONE team per game.
CREATE TABLE ROSTER (
    roster_id INT PRIMARY KEY AUTO_INCREMENT,
    player_id INT NOT NULL,
    team_id   INT NOT NULL,
    game_id   INT NOT NULL,
    join_date DATE,
    role      VARCHAR(50),
    UNIQUE (player_id, game_id),
    CONSTRAINT fk_roster_player FOREIGN KEY (player_id) REFERENCES PLAYER(player_id) ON DELETE CASCADE,
    CONSTRAINT fk_roster_team   FOREIGN KEY (team_id)   REFERENCES TEAM(team_id)     ON DELETE CASCADE,
    CONSTRAINT fk_roster_game   FOREIGN KEY (game_id)   REFERENCES GAME(game_id)     ON DELETE CASCADE);

-- TABLE: TOURNAMENT
-- CHECK ensures end_date >= start_date.Vice Versa Can never Be true.
CREATE TABLE TOURNAMENT (
    tournament_id    INT           PRIMARY KEY AUTO_INCREMENT,
    tournament_name  VARCHAR(150)  NOT NULL,
    game_id          INT           NOT NULL,
    start_date       DATE          NOT NULL,
    end_date         DATE          NOT NULL,
    location         VARCHAR(100)  NOT NULL,
    format           VARCHAR(60)   NOT NULL,
    total_prize_pool DECIMAL(12,2) NOT NULL,--Using 12,2 Since the value here can be large
    CONSTRAINT chk_tournament_dates CHECK (end_date >= start_date),
    



CONSTRAINT fk_tournament_game   FOREIGN KEY (game_id) REFERENCES GAME(game_id) ON DELETE RESTRICT
);
-- TABLE: TOURNAMENT_REGISTRATION
-- UNIQUE(tournament_id, team_id) prevents duplicate registrations.
CREATE TABLE TOURNAMENT_REGISTRATION (
    registration_id INT PRIMARY KEY AUTO_INCREMENT,
    tournament_id INT NOT NULL,
    team_id INT NOT NULL,
    registration_date DATE NOT NULL,
    status  VARCHAR(20) NOT NULL DEFAULT 'Pending',
    UNIQUE (tournament_id, team_id),
    CONSTRAINT fk_reg_tournament FOREIGN KEY (tournament_id) REFERENCES TOURNAMENT(tournament_id) ON DELETE CASCADE,
    CONSTRAINT fk_reg_team FOREIGN KEY (team_id) REFERENCES TEAM(team_id) ON DELETE CASCADE);

-- TABLE: MATCH  ( Delimiited Variable)
-- CHECK prevents a team from facing itself.
-- Winner validation enforced via TRIGGERS (MySQL limitation).
CREATE TABLE `MATCH` (
    match_id INT PRIMARY KEY AUTO_INCREMENT,
    tournament_id  INT NOT NULL,
    team1_id INT NOT NULL,
    team2_id INT NOT NULL,
    match_date DATETIME NOT NULL,
    stage VARCHAR(50) NOT NULL,
    winner_team_id INT  NULL,
    score VARCHAR(20) NULL,
    CONSTRAINT chk_different_teams CHECK (team1_id != team2_id),
    CONSTRAINT fk_match_tournament FOREIGN KEY (tournament_id)  REFERENCES TOURNAMENT(tournament_id) ON DELETE CASCADE,
    CONSTRAINT fk_match_team1 FOREIGN KEY (team1_id) REFERENCES TEAM(team_id) ON DELETE RESTRICT,
    CONSTRAINT fk_match_team2 FOREIGN KEY (team2_id) REFERENCES TEAM(team_id) ON DELETE RESTRICT,
    CONSTRAINT fk_match_winner FOREIGN KEY (winner_team_id) REFERENCES TEAM(team_id)             ON DELETE SET NULL);

-- TABLE: PRIZE_DISTRIBUTION
-- FIX : Added UNIQUE(tournament_id, team_id) in addition to
--        existing UNIQUE(tournament_id, position).
CREATE TABLE PRIZE_DISTRIBUTION (
    prize_id      INT           PRIMARY KEY AUTO_INCREMENT,
    tournament_id INT           NOT NULL,
    team_id       INT           NOT NULL,
    position      TINYINT       NOT NULL,
    prize_amount  DECIMAL(12,2) NOT NULL,
    UNIQUE (tournament_id, position),
    

UNIQUE (tournament_id, team_id),
CONSTRAINT fk_prize_tournament FOREIGN KEY (tournament_id) REFERENCES TOURNAMENT(tournament_id) ON DELETE CASCADE,
CONSTRAINT fk_prize_team FOREIGN KEY (team_id) REFERENCES TEAM(team_id) ON DELETE CASCADE);

-- TABLE: SPONSOR
CREATE TABLE SPONSOR (
    sponsor_id    INT          PRIMARY KEY AUTO_INCREMENT,
    sponsor_name  VARCHAR(100) NOT NULL UNIQUE,
    industry      VARCHAR(80)  NOT NULL,
    contact_email VARCHAR(100) NOT NULL
);
-- TABLE: SPONSORSHIP
-- FIX 5: Added CHECK(contract_end >= contract_start).
-- UNIQUE(sponsor_id, tournament_id) prevents duplicate contracts.

CREATE TABLE SPONSORSHIP (
    sponsorship_id INT PRIMARY KEY AUTO_INCREMENT,
    sponsor_id INT NOT NULL,
    tournament_id INT NOT NULL,
    amount_contributed DECIMAL(12,2) NOT NULL,
    contract_start DATE NOT NULL,
    contract_end DATE NOT NULL,
    UNIQUE (sponsor_id, tournament_id),
    CONSTRAINT chk_contract_dates CHECK (contract_end >= contract_start),
    CONSTRAINT fk_sponsorship_sponsor FOREIGN KEY (sponsor_id)    REFERENCES SPONSOR(sponsor_id) ON DELETE RESTRICT,
    CONSTRAINT fk_sponsorship_tourn   FOREIGN KEY (tournament_id) REFERENCES TOURNAMENT(tournament_id) ON DELETE CASCADE);

