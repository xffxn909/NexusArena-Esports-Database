-- 7.1 Data Insertion Queries

-- 7.1.1 Insert a New Team
INSERT INTO TEAM (team_name, country)
VALUES ('Shadow Wolves', 'Europe');

-- 7.1.2 Insert a New Player
INSERT INTO PLAYER (username, nationality)
VALUES ('AceGamer', 'USA');

-- 7.1.3 Register a Team in a Tournament
INSERT INTO TOURNAMENT_REGISTRATION (team_id, tournament_id, registration_date)
VALUES (1, 2, '2026-04-01');

-- 7.2 Data Update Queries

-- 7.2.1 Update Team Region
UPDATE TEAM
SET country = 'Asia'
WHERE team_id = 1;

-- 7.2.2 Update Prize Amount
UPDATE PRIZE_DISTRIBUTION
SET prize_amount = prize_amount + 5000
WHERE tournament_id = 2;

-- 7.3 Data Deletion Queries

-- 7.3.1 Delete a Player
DELETE FROM PLAYER
WHERE player_id = 10;

-- 7.3.2 Remove Tournament Registration
DELETE FROM TOURNAMENT_REGISTRATION
WHERE team_id = 1 AND tournament_id = 2;

-- 7.4 Analytical Queries (Reports)

-- 7.4.1 Total Earnings of Each Team
SELECT t.team_name,
SUM(pd.prize_amount) AS total_earnings
FROM TEAM t
LEFT JOIN PRIZE_DISTRIBUTION pd
ON t.team_id = pd.team_id
GROUP BY t.team_id, t.team_name
ORDER BY total_earnings DESC;

-- 7.4.2 Top 3 Performing Teams
SELECT t.team_name,
COUNT(m.match_id) AS wins
FROM MATCH m
JOIN TEAM t
ON m.winner_team_id = t.team_id
GROUP BY t.team_id, t.team_name
ORDER BY wins DESC
LIMIT 3;

-- 7.4.3 Tournament with Highest Prize Pool
SELECT tournament_name, total_prize_pool
FROM TOURNAMENT
ORDER BY total_prize_pool DESC
LIMIT 1;

-- 7.4.4 Total Sponsorship per Tournament
SELECT t.tournament_name,
SUM(s.amount_contributed) AS total_sponsorship
FROM TOURNAMENT t
JOIN SPONSORSHIP s
ON t.tournament_id = s.tournament_id
GROUP BY t.tournament_id, t.tournament_name;

-- 7.4.5 Player Earnings Leaderboard
SELECT *
FROM vw_player_earnings
ORDER BY total_earnings DESC;

-- 7.4.6 Teams with No Earnings
SELECT t.team_name
FROM TEAM t
LEFT JOIN PRIZE_DISTRIBUTION pd
ON t.team_id = pd.team_id
WHERE pd.team_id IS NULL;

-- 7.4.7 Most Popular Game (by Number of Tournaments)
SELECT g.game_name,
COUNT(t.tournament_id) AS total_tournaments
FROM GAME g
LEFT JOIN TOURNAMENT t
ON g.game_id = t.game_id
GROUP BY g.game_id, g.game_name
ORDER BY total_tournaments DESC
LIMIT 1;