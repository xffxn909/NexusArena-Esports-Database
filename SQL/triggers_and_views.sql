-- INDEXES
-- For fast searching 
CREATE INDEX idx_prize_team       ON PRIZE_DISTRIBUTION(team_id);
CREATE INDEX idx_prize_tournament ON PRIZE_DISTRIBUTION(tournament_id);
CREATE INDEX idx_sponsor_tourn    ON SPONSORSHIP(tournament_id);
-- ============================================================
-- TRIGGERS — Winner Validation for MATCH
-- A new concept learnt for Optimization of databases
-- ============================================================
DELIMITER $$
CREATE TRIGGER trg_match_winner_insert
BEFORE INSERT ON `MATCH`
FOR EACH ROW
BEGIN
    IF NEW.winner_team_id IS NOT NULL AND
       NEW.winner_team_id NOT IN (NEW.team1_id, NEW.team2_id) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Winner must be one of the two competing teams';
    

END IF;
END$$

CREATE TRIGGER trg_match_winner_update
BEFORE UPDATE ON `MATCH`
FOR EACH ROW
BEGIN
    IF NEW.winner_team_id IS NOT NULL AND
       NEW.winner_team_id NOT IN (NEW.team1_id, NEW.team2_id) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Winner must be one of the two competing teams';
    END IF;
END$$
DELIMITER ;

-- VIEWS(Using Views to compute the Derived data on demand 
-- instead of Storing it in a Seprate attribute)

CREATE VIEW vw_player_earnings AS
SELECT p.player_id, p.username, p.real_name,
       COALESCE(SUM(pd.prize_amount), 0) AS total_earnings
FROM PLAYER p
LEFT JOIN ROSTER r             ON p.player_id = r.player_id
LEFT JOIN PRIZE_DISTRIBUTION pd ON r.team_id  = pd.team_id
GROUP BY p.player_id, p.username, p.real_name;

CREATE VIEW vw_team_earnings AS
SELECT t.team_id, t.team_name, t.country,
       COALESCE(SUM(pd.prize_amount), 0) AS total_prize_money
FROM TEAM t
LEFT JOIN PRIZE_DISTRIBUTION pd ON t.team_id = pd.team_id
GROUP BY t.team_id, t.team_name, t.country;

CREATE VIEW vw_global_leaderboard AS
SELECT t.team_name, t.country,
       COALESCE(SUM(pd.prize_amount), 0) AS total_earnings,
       COUNT(pd.prize_id)                AS podium_finishes
FROM TEAM t
LEFT JOIN PRIZE_DISTRIBUTION pd ON t.team_id = pd.team_id
GROUP BY t.team_id, t.team_name, t.country;
