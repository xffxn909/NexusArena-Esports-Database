USE NexusArena;
-- INSERT: GAME

INSERT INTO GAME (game_name, publisher, platform, genre) VALUES
    ('Valorant', 'Riot Games', 'PC', 'Tactical Shooter'),
    ('Counter-Strike 2',  'Valve', 'PC', 'Tactical Shooter'),
    ('League of Legends', 'Riot Games', 'PC', 'MOBA'),
    ('Dota 2', 'Valve', 'PC', 'MOBA'),
    ('Rocket League', 'Psyonix', 'PC/Console', 'Sports'),
    ('Fortnite', 'Epic Games', 'PC/Console', 'Battle Royale'),
    ('Apex Legends', 'Respawn', 'PC/Console', 'Battle Royale'),
    ('Overwatch 2', 'Blizzard', 'PC/Console', 'Team Shooter');

-- INSERT: TEAM

INSERT INTO TEAM (team_name, founded_date, coach_name, country) VALUES
    ('Team Liquid',   '2000-01-01', 'David Holden',   'Netherlands'),
    ('Natus Vincere', '2009-12-17', 'Andrey Bloknot', 'Ukraine'),
    ('Cloud9',        '2012-05-15', 'James Smith',    'USA'),
    ('Fnatic',        '2004-07-23', 'Louis Dobson',   'United Kingdom'),
    ('Astralis',      '2016-01-17', 'Lars Doere',     'Denmark'),
    ('FaZe Clan',     '2016-01-30', 'Mads Lauridsen', 'USA'),
    ('T1',            '2019-09-04', 'Park Jae-hyun',  'South Korea'),
    ('G2 Esports',    '2013-11-25', 'Fabian Lohmann', 'Spain'),
    ('100 Thieves',   '2017-11-06', 'Jordan Terry',   'USA'),
    ('Sentinels',     '2018-01-01', 'Adam Kaplan',    'USA'),
    ('NRG Esports',   '2015-11-11', 'Chet Singh',     'USA'),
    ('Team Vitality', '2013-11-23', 'Nicolas Maurer', 'France');

-- INSERT: PLAYER
INSERT INTO PLAYER (username, real_name, nationality, email, player_rank, date_of_birth) VALUES
    ('s1mple', 'Oleksandr Kostyliev','Ukrainian','s1mple@nexus.gg','Global Elite', '1997-10-02'),
    ('ZywOo','Mathieu Herbaut','French','zywoo@nexus.gg','Global Elite', '2000-11-09'),
    ('NiKo','Nikola Kovac','Bosnian','niko@nexus.gg','Global Elite', '1997-02-16'),
    ('device','Nicolai Reedtz','Danish','device@nexus.gg','Global Elite', '1996-09-08'),
    ('Faker',    'Lee Sang-hyeok',      'Korean',    'faker@nexus.gg',    'Challenger',   '1996-05-07'),
    


('Caps', 'Rasmus Borregaard', 'Danish', 'caps@nexus.gg', 'Grandmaster', '1999-11-04'),
    ('TenZ', 'Tyson Ngo', 'Canadian',  'tenz@nexus.gg', 'Radiant',      '2001-05-05'),
    ('ScreaM', 'Adil Benrlitom', 'Belgian', 'scream@nexus.gg', 'Radiant', '1994-03-02'),
    ('Yay', 'Jaccob Whitelaw', 'American', 'yay@nexus.gg', 'Radiant', '1999-07-10'),
    ('ShahZaM', 'Shahzeeb Khan', 'American', 'shahzam@nexus.gg', 'Radiant', '1995-06-24'),
    ('Bugha', 'Kyle Giersdorf', 'American', 'bugha@nexus.gg', 'Champion','2002-12-30'),
    ('Aqua', 'David Wang', 'Austrian', 'aqua@nexus.gg', 'Champion',     '2004-03-21'),
    ('Shroud', 'Michael Grzesiek',    'Canadian',  'shroud@nexus.gg',   'Radiant',      '1994-06-02'),
    ('Stewie2K', 'Jacky Yip', 'American', 'stewie2k@nexus.gg', 'Global Elite', '1998-01-07'),
    ('Xyp9x',    'Andreas Hojsleth', 'Danish', 'xyp9x@nexus.gg', 'Global Elite', '1995-09-11');

-- INSERT: ROSTER

INSERT INTO ROSTER (player_id, team_id, game_id, join_date, role) VALUES
    (1,2,2, '2017-01-01', 'AWPer'),
    (2,12,2, '2019-09-01', 'AWPer'),
    (3,  8,  2, '2021-01-01', 'Entry Fragger'),
    (4,  5,  2, '2016-01-17', 'AWPer'),
    (5,  7,  3, '2013-02-01', 'Mid Laner'),
    (6,  8,  3, '2018-11-22', 'Mid Laner'),
    (7,  10, 1, '2021-02-10', 'Duelist'),
    (8,  8,  1, '2022-01-15', 'Duelist'),
    (9,  9,  1, '2022-06-01', 'Duelist'),
    (10, 10, 1, '2020-09-12', 'IGL'),
    (11, 3,  6, '2019-09-01', 'Solo Carry'),
    (12, 6,  6, '2022-04-01', 'Solo Carry'),
    (13, 3,  2, '2020-03-01', 'Rifler'),
    (14, 3,  2, '2019-07-01', 'Entry Fragger'),
    (15, 5,  2, '2016-01-17', 'Support');

-- INSERT: TOURNAMENT

INSERT INTO TOURNAMENT (tournament_name, game_id, start_date, end_date, location, format, total_prize_pool) VALUES
    ('VCT Masters Tokyo 2024', 1,'2024-06-02','2024-06-16','Tokyo, Japan', 'Double Elimination', 500000.00),
    ('ESL Pro League Season 19', 2,'2024-03-20','2024-04-07','Malta', 'Swiss + Playoffs',  835000.00),
    ('IEM Cologne 2024', 2,'2024-07-10','2024-07-21','Cologne, Germany',    'Single Elimination',1000000.00),
    ('LoL World Championship 2024', 3,'2024-09-25','2024-11-02','London, UK', 'Group + Knockout',  2250000.00),
    ('Rocket League World Champ 2024', 5,'2024-08-08','2024-08-11','Riyadh, Saudi Arabia','Double Elimination', 600000.00),
    ('Fortnite World Cup 2024', 6,'2024-07-26','2024-07-28','New York, USA',       'Solo Qualifiers',   3000000.00),
    ('BLAST Premier World Final 2024', 2,'2024-12-11','2024-12-15','Abu Dhabi, UAE',      'Single Elimination',1000000.00),
    ('VCT Champions 2024', 1,'2024-08-01','2024-08-25','Seoul, South Korea', 'Double Elimination', 500000.00);

-- INSERT: TOURNAMENT_REGISTRATION
INSERT INTO TOURNAMENT_REGISTRATION (tournament_id, team_id, registration_date, status) VALUES
    (1,10,'2024-05-01','Confirmed'), (1,9,'2024-05-02','Confirmed'),
    (1,8,'2024-05-03','Confirmed'),  (1,11,'2024-05-04','Confirmed'),
    (2,2,'2024-03-01','Confirmed'),  (2,5,'2024-03-02','Confirmed'),
    (2,6,'2024-03-03','Confirmed'),  (2,12,'2024-03-04','Confirmed'),
    (3,2,'2024-06-20','Confirmed'),  (3,5,'2024-06-21','Confirmed'),
    (3,8,'2024-06-22','Confirmed'),  (3,6,'2024-06-23','Confirmed'),
    (4,7,'2024-09-01','Confirmed'),  (4,8,'2024-09-02','Confirmed'),
    (4,1,'2024-09-03','Confirmed'),  (4,4,'2024-09-04','Confirmed'),
    (5,1,'2024-07-20','Confirmed'),  (5,3,'2024-07-21','Confirmed'),
    (6,3,'2024-07-01','Confirmed'),  (6,6,'2024-07-02','Confirmed'),
    (7,2,'2024-11-15','Confirmed'),  (7,5,'2024-11-16','Confirmed'),
    (7,6,'2024-11-17','Confirmed'),  (7,12,'2024-11-18','Confirmed'),
    (8,10,'2024-07-10','Confirmed'), (8,9,'2024-07-11','Confirmed'),
    (8,8,'2024-07-12','Confirmed'),  (8,11,'2024-07-13','Confirmed');

-- INSERT: MATCH

INSERT INTO `MATCH` (tournament_id, team1_id, team2_id, match_date, stage, winner_team_id, score) VALUES
    (1,10,9, '2024-06-10 14:00:00','Semifinal',10,'2-1'),
    (1,8,11, '2024-06-10 17:00:00','Semifinal', 8,'2-0'),
    (1,10,8, '2024-06-16 18:00:00','Final', 10,'3-1'),
    (2,2,12, '2024-04-01 13:00:00','Semifinal', 2,'2-0'),
    (2,5,6,  '2024-04-01 16:00:00','Semifinal', 5,'2-1'),
    (2,2,5,  '2024-04-07 17:00:00','Final', 5,'3-2'),
    (3,2,8,  '2024-07-18 13:00:00','Semifinal', 2,'2-1'),
    (3,5,6,  '2024-07-18 16:00:00','Semifinal', 6,'2-0'),
    (3,2,6,  '2024-07-21 17:00:00','Final', 2,'3-1'),
    (4,7,8,  '2024-10-30 14:00:00','Semifinal', 7,'3-1'),
    (4,1,4,  '2024-10-30 17:00:00','Semifinal', 1,'3-2'),
    (4,7,1,  '2024-11-02 18:00:00','Final', 7,'3-0'),
    (5,1,3,  '2024-08-10 15:00:00','Final', 1,'4-2'),
    (6,3,6,  '2024-07-28 16:00:00','Final', 6,'1-0'),
    (7,2,6,  '2024-12-13 14:00:00','Semifinal', 2,'2-0'),
    (7,5,12, '2024-12-13 17:00:00','Semifinal', 5,'2-1'),
    (7,2,5,  '2024-12-15 18:00:00','Final',      2,'3-2'),
    (8,10,8, '2024-08-22 14:00:00','Semifinal', 8,'2-1'),
    (8,9,11, '2024-08-22 17:00:00','Semifinal', 9,'2-0'),
    (8,8,9,  '2024-08-25 18:00:00','Final',      8,'3-2');




-- INSERT: PRIZE_DISTRIBUTION
INSERT INTO PRIZE_DISTRIBUTION (tournament_id, team_id, position, prize_amount) VALUES
    (1,10,1,200000.00),(1,8,2,140000.00), (1,9,3,80000.00),
    (2,5,1,350000.00),(2,2,2,200000.00), (2,6,3,100000.00),
    (3,2,1,400000.00),(3,6,2,250000.00), (3,5,3,125000.00),
    (4,7,1,900000.00),(4,1,2,450000.00), (4,8,3,225000.00),
    (5,1,1,300000.00),(5,3,2,150000.00),
    (6,6,1,1500000.00),(6,3,2,750000.00),
    (7,2,1,425000.00),(7,5,2,212500.00),(7,6,3,106250.00),
    (8,8,1,200000.00),(8,9,2,140000.00),(8,10,3,80000.00);

-- INSERT: SPONSOR
INSERT INTO SPONSOR (sponsor_name, industry, contact_email) VALUES
    ('Intel','Semiconductors','esports@intel.com'),
    ('Red Bull','Beverages','gaming@redbull.com'),
    ('SteelSeries','Gaming Peripherals','partners@steelseries.com'),
    ('ASUS ROG','Gaming Hardware','rog@asus.com'),
    ('HyperX','Gaming Peripherals', 'sponsorship@hyperx.com'),
    ('Secretlab','Gaming Furniture','deals@secretlab.co'),
    ('Monster Energy', 'Beverages','gaming@monsterenergy.com'),
    ('Mastercard','Financial Services', 'esports@mastercard.com'),
    ('BMW','Automotive','esports@bmw.com'),
    ('Logitech G','Gaming Peripherals','biz@logitechg.com');
-- INSERT: SPONSORSHIP
INSERT INTO SPONSORSHIP (sponsor_id, tournament_id, amount_contributed, contract_start, contract_end) VALUES
    (1,1,80000.00,'2024-04-01','2024-06-30'),
    (2,1,60000.00,'2024-04-01','2024-06-30'),
    (3,1,40000.00,'2024-04-01','2024-06-30'),
    (4,2,200000.00,'2024-02-01','2024-04-30'),
    (5,2,150000.00,'2024-02-01','2024-04-30'),
    (6,2,80000.00,'2024-02-01','2024-04-30'),
    (1,3,300000.00,'2024-05-01','2024-07-31'),
    (7,3,200000.00,'2024-05-01','2024-07-31'),
    (8,3,150000.00,'2024-05-01','2024-07-31'),
    (9,4,500000.00,'2024-08-01','2024-11-30'),
    (8,4,400000.00,'2024-08-01','2024-11-30'),
    (2,4,300000.00,'2024-08-01','2024-11-30'),
    (10,5,150000.00,'2024-06-01','2024-08-31'),
    (4,5,100000.00,'2024-06-01','2024-08-31'),
    (2,6,800000.00,'2024-05-01','2024-08-31'),
    (9,6,600000.00,'2024-05-01','2024-08-31'),
    (7,7,250000.00,'2024-10-01','2024-12-31'),
    (5,7,180000.00,'2024-10-01','2024-12-31'),
    (6,7,120000.00,'2024-10-01','2024-12-31'),
    (3,8,90000.00,'2024-06-01','2024-08-31'),
    (10,8,70000.00,'2024-06-01','2024-08-31'),
    (1,8,50000.00,'2024-06-01','2024-08-31')
