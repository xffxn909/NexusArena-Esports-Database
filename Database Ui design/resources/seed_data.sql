-- ── GAMES ──────────────────────────────────────────────────
INSERT INTO GAME (game_name, publisher, platform, genre) VALUES
('Valorant', 'Riot Games', 'PC', 'Tactical Shooter'),
('Counter-Strike 2', 'Valve', 'PC', 'Tactical Shooter'),
('League of Legends', 'Riot Games', 'PC', 'MOBA'),
('Dota 2', 'Valve', 'PC', 'MOBA'),
('Overwatch 2', 'Blizzard', 'PC/Console', 'Hero Shooter'),
('Apex Legends', 'EA', 'PC/Console', 'Battle Royale'),
('Rocket League', 'Psyonix', 'PC/Console', 'Sports');

-- ── PLAYERS ────────────────────────────────────────────────
INSERT INTO PLAYER (username, real_name, nationality, email, player_rank, date_of_birth) VALUES
('TenZ', 'Tyson Ngo', 'Canada', 'tenz@nexus.gg', 'Radiant', '1999-05-05'),
('s1mple', 'Oleksandr Kostyliev', 'Ukraine', 's1mple@nexus.gg', 'Global Elite', '1997-10-02'),
('Faker', 'Lee Sang-hyeok', 'South Korea', 'faker@nexus.gg', 'Challenger', '1996-05-07'),
('N0tail', 'Johan Sundstein', 'Denmark', 'n0tail@nexus.gg', 'Immortal', '1993-10-08'),
('carpe', 'Lee Jae-hyeok', 'South Korea', 'carpe@nexus.gg', 'Top 500', '1998-09-21'),
('Shroud', 'Michael Grzesiek', 'Canada', 'shroud@nexus.gg', 'Global Elite', '1994-06-02'),
('Aspas', 'Erick Santos', 'Brazil', 'aspas@nexus.gg', 'Radiant', '2002-06-15'),
('ZywOo', 'Mathieu Herbaut', 'France', 'zywoo@nexus.gg', 'Global Elite', '2000-11-16'),
('Yay', 'Jaccob Whiteaker', 'USA', 'yay@nexus.gg', 'Radiant', '1999-01-01'),
('Fallen', 'Gabriel Toledo', 'Brazil', 'fallen@nexus.gg', 'Global Elite', '1991-05-30'),
('Ana', 'Anathan Pham', 'Australia', 'ana@nexus.gg', 'Immortal', '1999-04-29'),
('Bugha', 'Kyle Giersdorf', 'USA', 'bugha@nexus.gg', 'Champion', '2002-12-30');

-- ── TEAMS ──────────────────────────────────────────────────
INSERT INTO TEAM (team_name, founded_date, coach_name, country) VALUES
('Cloud9', '2013-05-01', 'Reignover', 'USA'),
('Fnatic', '2004-07-23', 'Boaster', 'UK'),
('Team Liquid', '2000-10-10', 'dimasick', 'Netherlands'),
('T1', '2002-02-12', 'kkOma', 'South Korea'),
('Natus Vincere', '2009-12-17', 'B1ad3', 'Ukraine'),
('Sentinels', '2016-10-29', 'SyykoNT', 'USA'),
('LOUD', '2019-01-01', 'Bezn1', 'Brazil'),
('OG', '2015-10-31', 'Ceb', 'Europe');

-- ── ROSTERS ────────────────────────────────────────────────
INSERT INTO ROSTER (player_id, team_id, game_id, join_date, role) VALUES
(1, 6, 1, '2023-01-15', 'Duelist'),
(2, 5, 2, '2019-01-01', 'AWPer'),
(3, 4, 3, '2013-02-01', 'Mid Laner'),
(4, 8, 4, '2015-10-31', 'Support'),
(5, 1, 5, '2022-06-01', 'DPS'),
(6, 6, 1, '2022-04-10', 'Controller'),
(7, 7, 1, '2022-01-20', 'Duelist'),
(8, 3, 2, '2023-09-01', 'Rifler'),
(9, 6, 1, '2022-03-01', 'Sentinel'),
(10, 7, 2, '2021-06-01', 'IGL'),
(11, 8, 4, '2018-06-01', 'Carry'),
(12, 1, 5, '2023-01-15', 'DPS');

-- ── TOURNAMENTS ────────────────────────────────────────────
INSERT INTO TOURNAMENT (tournament_name, game_id, start_date, end_date, location, format, total_prize_pool) VALUES
('VCT Champions 2024', 1, '2024-08-01', '2024-08-25', 'Seoul, South Korea', 'Double Elimination', 2250000.00),
('CS2 Major Copenhagen', 2, '2024-03-17', '2024-03-31', 'Copenhagen, Denmark', 'Swiss Stage', 1250000.00),
('Worlds 2024', 3, '2024-09-25', '2024-11-02', 'London, UK', 'Group Stage + Knockout', 2225000.00),
('The International 2024', 4, '2024-09-04', '2024-09-15', 'Seattle, USA', 'Group Stage + Main Event', 18000000.00),
('OWL Grand Finals 2024', 5, '2024-10-01', '2024-10-05', 'Toronto, Canada', 'Best of 7', 1500000.00),
('VCT Masters Shanghai', 1, '2024-04-14', '2024-04-28', 'Shanghai, China', 'Double Elimination', 1000000.00),
('BLAST Premier World Final', 2, '2024-12-11', '2024-12-15', 'Abu Dhabi, UAE', 'Single Elimination', 1000000.00);

-- ── TOURNAMENT REGISTRATIONS ───────────────────────────────
INSERT INTO TOURNAMENT_REGISTRATION (tournament_id, team_id, registration_date, status) VALUES
(1, 6, '2024-06-01', 'Confirmed'),
(1, 7, '2024-06-05', 'Confirmed'),
(1, 2, '2024-06-10', 'Confirmed'),
(2, 5, '2024-01-15', 'Confirmed'),
(2, 3, '2024-01-20', 'Confirmed'),
(3, 4, '2024-07-01', 'Confirmed'),
(4, 8, '2024-07-15', 'Confirmed'),
(5, 1, '2024-08-01', 'Confirmed'),
(6, 6, '2024-03-01', 'Confirmed'),
(6, 7, '2024-03-05', 'Confirmed'),
(7, 5, '2024-10-01', 'Confirmed'),
(7, 3, '2024-10-05', 'Pending'),
(4, 3, '2024-07-18', 'Pending');

-- ── MATCHES ────────────────────────────────────────────────
INSERT INTO `MATCH` (tournament_id, team1_id, team2_id, match_date, stage, winner_team_id, score) VALUES
(1, 6, 7, '2024-08-10 14:00:00', 'Quarter-Final', 6, '2-1'),
(1, 2, 6, '2024-08-18 16:00:00', 'Semi-Final', 6, '3-1'),
(1, 6, 7, '2024-08-25 18:00:00', 'Grand Final', 6, '3-2'),
(2, 5, 3, '2024-03-25 12:00:00', 'Quarter-Final', 5, '2-0'),
(2, 5, 3, '2024-03-30 15:00:00', 'Grand Final', 5, '2-1'),
(3, 4, 1, '2024-10-20 13:00:00', 'Semi-Final', 4, '3-1'),
(4, 8, 3, '2024-09-12 11:00:00', 'Upper Bracket Final', 8, '2-0'),
(4, 8, 5, '2024-09-15 17:00:00', 'Grand Final', 8, '3-2'),
(5, 1, 2, '2024-10-04 19:00:00', 'Grand Final', 1, '4-3'),
(6, 6, 7, '2024-04-20 14:00:00', 'Semi-Final', 7, '2-1'),
(6, 7, 2, '2024-04-28 18:00:00', 'Grand Final', 7, '3-0'),
(7, 5, 3, '2024-12-14 16:00:00', 'Grand Final', NULL, NULL);

-- ── PRIZE DISTRIBUTION ─────────────────────────────────────
INSERT INTO PRIZE_DISTRIBUTION (tournament_id, team_id, position, prize_amount) VALUES
(1, 6, 1, 1000000.00),
(1, 7, 2, 500000.00),
(1, 2, 3, 250000.00),
(2, 5, 1, 500000.00),
(2, 3, 2, 250000.00),
(3, 4, 1, 890000.00),
(4, 8, 1, 8500000.00),
(4, 3, 2, 3000000.00),
(4, 5, 3, 1500000.00),
(5, 1, 1, 750000.00),
(5, 2, 2, 350000.00),
(6, 7, 1, 500000.00),
(6, 6, 2, 250000.00);

-- ── SPONSORS ───────────────────────────────────────────────
INSERT INTO SPONSOR (sponsor_name, industry, contact_email) VALUES
('Intel', 'Technology', 'esports@intel.com'),
('Red Bull', 'Energy Drinks', 'gaming@redbull.com'),
('HyperX', 'Gaming Peripherals', 'partnerships@hyperx.com'),
('Secretlab', 'Gaming Furniture', 'esports@secretlab.co'),
('BMW', 'Automotive', 'esports@bmw.com');

-- ── SPONSORSHIPS ───────────────────────────────────────────
INSERT INTO SPONSORSHIP (sponsor_id, tournament_id, amount_contributed, contract_start, contract_end) VALUES
(1, 1, 500000.00, '2024-01-01', '2024-12-31'),
(2, 1, 300000.00, '2024-06-01', '2024-09-30'),
(3, 2, 200000.00, '2024-01-01', '2024-06-30'),
(1, 4, 1000000.00, '2024-01-01', '2024-12-31'),
(4, 3, 400000.00, '2024-07-01', '2024-12-31'),
(5, 5, 600000.00, '2024-01-01', '2024-12-31'),
(2, 6, 250000.00, '2024-03-01', '2024-05-31'),
(3, 7, 150000.00, '2024-10-01', '2024-12-31');

-