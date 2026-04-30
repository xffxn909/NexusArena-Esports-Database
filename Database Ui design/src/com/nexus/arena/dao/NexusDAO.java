package com.nexus.arena.dao;

import com.nexus.arena.model.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Unified Data Access Object for all NexusArena tables and views.
 * Provides full CRUD for 10 tables and read access for 3 computed views.
 */
public class NexusDAO {

    // ════════════════════════════════════════════════════════════════
    // GAME — CRUD
    // ════════════════════════════════════════════════════════════════

    public List<Game> getAllGames() {
        List<Game> list = new ArrayList<>();
        String sql = "SELECT * FROM GAME ORDER BY game_id";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Game(
                    rs.getInt("game_id"), rs.getString("game_name"),
                    rs.getString("publisher"), rs.getString("platform"),
                    rs.getString("genre")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insertGame(Game g) {
        String sql = "INSERT INTO GAME (game_name, publisher, platform, genre) VALUES (?,?,?,?)";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, g.getGameName());
            ps.setString(2, g.getPublisher());
            ps.setString(3, g.getPlatform());
            ps.setString(4, g.getGenre());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateGame(Game g) {
        String sql = "UPDATE GAME SET game_name=?, publisher=?, platform=?, genre=? WHERE game_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, g.getGameName());
            ps.setString(2, g.getPublisher());
            ps.setString(3, g.getPlatform());
            ps.setString(4, g.getGenre());
            ps.setInt(5, g.getGameId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteGame(int id) {
        String sql = "DELETE FROM GAME WHERE game_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ════════════════════════════════════════════════════════════════
    // PLAYER — CRUD
    // ════════════════════════════════════════════════════════════════

    public List<Player> getAllPlayers() {
        List<Player> list = new ArrayList<>();
        String sql = "SELECT * FROM PLAYER ORDER BY player_id";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Player(
                    rs.getInt("player_id"), rs.getString("username"),
                    rs.getString("real_name"), rs.getString("nationality"),
                    rs.getString("email"), rs.getString("player_rank"),
                    rs.getDate("date_of_birth") != null ? rs.getDate("date_of_birth").toLocalDate() : null));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insertPlayer(Player p) {
        String sql = "INSERT INTO PLAYER (username, real_name, nationality, email, player_rank, date_of_birth) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, p.getUsername());
            ps.setString(2, p.getRealName());
            ps.setString(3, p.getNationality());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getPlayerRank());
            ps.setDate(6, p.getDateOfBirth() != null ? Date.valueOf(p.getDateOfBirth()) : null);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updatePlayer(Player p) {
        String sql = "UPDATE PLAYER SET username=?, real_name=?, nationality=?, email=?, player_rank=?, date_of_birth=? WHERE player_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, p.getUsername());
            ps.setString(2, p.getRealName());
            ps.setString(3, p.getNationality());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getPlayerRank());
            ps.setDate(6, p.getDateOfBirth() != null ? Date.valueOf(p.getDateOfBirth()) : null);
            ps.setInt(7, p.getPlayerId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deletePlayer(int id) {
        String sql = "DELETE FROM PLAYER WHERE player_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ════════════════════════════════════════════════════════════════
    // TEAM — CRUD
    // ════════════════════════════════════════════════════════════════

    public List<Team> getAllTeams() {
        List<Team> list = new ArrayList<>();
        String sql = "SELECT * FROM TEAM ORDER BY team_id";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Team(
                    rs.getInt("team_id"), rs.getString("team_name"),
                    rs.getDate("founded_date") != null ? rs.getDate("founded_date").toLocalDate() : null,
                    rs.getString("coach_name"), rs.getString("country")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insertTeam(Team t) {
        String sql = "INSERT INTO TEAM (team_name, founded_date, coach_name, country) VALUES (?,?,?,?)";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, t.getTeamName());
            ps.setDate(2, t.getFoundedDate() != null ? Date.valueOf(t.getFoundedDate()) : null);
            ps.setString(3, t.getCoachName());
            ps.setString(4, t.getCountry());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateTeam(Team t) {
        String sql = "UPDATE TEAM SET team_name=?, founded_date=?, coach_name=?, country=? WHERE team_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, t.getTeamName());
            ps.setDate(2, t.getFoundedDate() != null ? Date.valueOf(t.getFoundedDate()) : null);
            ps.setString(3, t.getCoachName());
            ps.setString(4, t.getCountry());
            ps.setInt(5, t.getTeamId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteTeam(int id) {
        String sql = "DELETE FROM TEAM WHERE team_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ════════════════════════════════════════════════════════════════
    // ROSTER — CRUD
    // ════════════════════════════════════════════════════════════════

    public List<Roster> getAllRosters() {
        List<Roster> list = new ArrayList<>();
        String sql = "SELECT * FROM ROSTER ORDER BY roster_id";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Roster(
                    rs.getInt("roster_id"), rs.getInt("player_id"),
                    rs.getInt("team_id"), rs.getInt("game_id"),
                    rs.getDate("join_date") != null ? rs.getDate("join_date").toLocalDate() : null,
                    rs.getString("role")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insertRoster(Roster r) {
        String sql = "INSERT INTO ROSTER (player_id, team_id, game_id, join_date, role) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, r.getPlayerId());
            ps.setInt(2, r.getTeamId());
            ps.setInt(3, r.getGameId());
            ps.setDate(4, r.getJoinDate() != null ? Date.valueOf(r.getJoinDate()) : null);
            ps.setString(5, r.getRole());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateRoster(Roster r) {
        String sql = "UPDATE ROSTER SET player_id=?, team_id=?, game_id=?, join_date=?, role=? WHERE roster_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, r.getPlayerId());
            ps.setInt(2, r.getTeamId());
            ps.setInt(3, r.getGameId());
            ps.setDate(4, r.getJoinDate() != null ? Date.valueOf(r.getJoinDate()) : null);
            ps.setString(5, r.getRole());
            ps.setInt(6, r.getRosterId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteRoster(int id) {
        String sql = "DELETE FROM ROSTER WHERE roster_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ════════════════════════════════════════════════════════════════
    // TOURNAMENT — CRUD
    // ════════════════════════════════════════════════════════════════

    public List<Tournament> getAllTournaments() {
        List<Tournament> list = new ArrayList<>();
        String sql = "SELECT * FROM TOURNAMENT ORDER BY tournament_id";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Tournament(
                    rs.getInt("tournament_id"), rs.getString("tournament_name"),
                    rs.getInt("game_id"),
                    rs.getDate("start_date").toLocalDate(), rs.getDate("end_date").toLocalDate(),
                    rs.getString("location"), rs.getString("format"),
                    rs.getBigDecimal("total_prize_pool")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insertTournament(Tournament t) {
        String sql = "INSERT INTO TOURNAMENT (tournament_name, game_id, start_date, end_date, location, format, total_prize_pool) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, t.getTournamentName());
            ps.setInt(2, t.getGameId());
            ps.setDate(3, Date.valueOf(t.getStartDate()));
            ps.setDate(4, Date.valueOf(t.getEndDate()));
            ps.setString(5, t.getLocation());
            ps.setString(6, t.getFormat());
            ps.setBigDecimal(7, t.getTotalPrizePool());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateTournament(Tournament t) {
        String sql = "UPDATE TOURNAMENT SET tournament_name=?, game_id=?, start_date=?, end_date=?, location=?, format=?, total_prize_pool=? WHERE tournament_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, t.getTournamentName());
            ps.setInt(2, t.getGameId());
            ps.setDate(3, Date.valueOf(t.getStartDate()));
            ps.setDate(4, Date.valueOf(t.getEndDate()));
            ps.setString(5, t.getLocation());
            ps.setString(6, t.getFormat());
            ps.setBigDecimal(7, t.getTotalPrizePool());
            ps.setInt(8, t.getTournamentId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteTournament(int id) {
        String sql = "DELETE FROM TOURNAMENT WHERE tournament_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ════════════════════════════════════════════════════════════════
    // TOURNAMENT_REGISTRATION — CRUD
    // ════════════════════════════════════════════════════════════════

    public List<TournamentRegistration> getAllRegistrations() {
        List<TournamentRegistration> list = new ArrayList<>();
        String sql = "SELECT * FROM TOURNAMENT_REGISTRATION ORDER BY registration_id";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new TournamentRegistration(
                    rs.getInt("registration_id"), rs.getInt("tournament_id"),
                    rs.getInt("team_id"),
                    rs.getDate("registration_date").toLocalDate(),
                    rs.getString("status")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insertRegistration(TournamentRegistration r) {
        String sql = "INSERT INTO TOURNAMENT_REGISTRATION (tournament_id, team_id, registration_date, status) VALUES (?,?,?,?)";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, r.getTournamentId());
            ps.setInt(2, r.getTeamId());
            ps.setDate(3, Date.valueOf(r.getRegistrationDate()));
            ps.setString(4, r.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateRegistration(TournamentRegistration r) {
        String sql = "UPDATE TOURNAMENT_REGISTRATION SET tournament_id=?, team_id=?, registration_date=?, status=? WHERE registration_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, r.getTournamentId());
            ps.setInt(2, r.getTeamId());
            ps.setDate(3, Date.valueOf(r.getRegistrationDate()));
            ps.setString(4, r.getStatus());
            ps.setInt(5, r.getRegistrationId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteRegistration(int id) {
        String sql = "DELETE FROM TOURNAMENT_REGISTRATION WHERE registration_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ════════════════════════════════════════════════════════════════
    // MATCH — CRUD (backtick quoted — reserved word)
    // ════════════════════════════════════════════════════════════════

    public List<Match> getAllMatches() {
        List<Match> list = new ArrayList<>();
        String sql = "SELECT * FROM `MATCH` ORDER BY match_id";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Integer winner = rs.getInt("winner_team_id");
                if (rs.wasNull()) winner = null;
                list.add(new Match(
                    rs.getInt("match_id"), rs.getInt("tournament_id"),
                    rs.getInt("team1_id"), rs.getInt("team2_id"),
                    rs.getTimestamp("match_date").toLocalDateTime(),
                    rs.getString("stage"), winner, rs.getString("score")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insertMatch(Match m) {
        String sql = "INSERT INTO `MATCH` (tournament_id, team1_id, team2_id, match_date, stage, winner_team_id, score) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, m.getTournamentId());
            ps.setInt(2, m.getTeam1Id());
            ps.setInt(3, m.getTeam2Id());
            ps.setTimestamp(4, Timestamp.valueOf(m.getMatchDate()));
            ps.setString(5, m.getStage());
            if (m.getWinnerTeamId() != null) ps.setInt(6, m.getWinnerTeamId());
            else ps.setNull(6, Types.INTEGER);
            ps.setString(7, m.getScore());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateMatch(Match m) {
        String sql = "UPDATE `MATCH` SET tournament_id=?, team1_id=?, team2_id=?, match_date=?, stage=?, winner_team_id=?, score=? WHERE match_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, m.getTournamentId());
            ps.setInt(2, m.getTeam1Id());
            ps.setInt(3, m.getTeam2Id());
            ps.setTimestamp(4, Timestamp.valueOf(m.getMatchDate()));
            ps.setString(5, m.getStage());
            if (m.getWinnerTeamId() != null) ps.setInt(6, m.getWinnerTeamId());
            else ps.setNull(6, Types.INTEGER);
            ps.setString(7, m.getScore());
            ps.setInt(8, m.getMatchId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteMatch(int id) {
        String sql = "DELETE FROM `MATCH` WHERE match_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ════════════════════════════════════════════════════════════════
    // PRIZE_DISTRIBUTION — CRUD
    // ════════════════════════════════════════════════════════════════

    public List<PrizeDistribution> getAllPrizes() {
        List<PrizeDistribution> list = new ArrayList<>();
        String sql = "SELECT * FROM PRIZE_DISTRIBUTION ORDER BY prize_id";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new PrizeDistribution(
                    rs.getInt("prize_id"), rs.getInt("tournament_id"),
                    rs.getInt("team_id"), rs.getInt("position"),
                    rs.getBigDecimal("prize_amount")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insertPrize(PrizeDistribution p) {
        String sql = "INSERT INTO PRIZE_DISTRIBUTION (tournament_id, team_id, position, prize_amount) VALUES (?,?,?,?)";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, p.getTournamentId());
            ps.setInt(2, p.getTeamId());
            ps.setInt(3, p.getPosition());
            ps.setBigDecimal(4, p.getPrizeAmount());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updatePrize(PrizeDistribution p) {
        String sql = "UPDATE PRIZE_DISTRIBUTION SET tournament_id=?, team_id=?, position=?, prize_amount=? WHERE prize_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, p.getTournamentId());
            ps.setInt(2, p.getTeamId());
            ps.setInt(3, p.getPosition());
            ps.setBigDecimal(4, p.getPrizeAmount());
            ps.setInt(5, p.getPrizeId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deletePrize(int id) {
        String sql = "DELETE FROM PRIZE_DISTRIBUTION WHERE prize_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ════════════════════════════════════════════════════════════════
    // SPONSOR — CRUD
    // ════════════════════════════════════════════════════════════════

    public List<Sponsor> getAllSponsors() {
        List<Sponsor> list = new ArrayList<>();
        String sql = "SELECT * FROM SPONSOR ORDER BY sponsor_id";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Sponsor(
                    rs.getInt("sponsor_id"), rs.getString("sponsor_name"),
                    rs.getString("industry"), rs.getString("contact_email")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insertSponsor(Sponsor s) {
        String sql = "INSERT INTO SPONSOR (sponsor_name, industry, contact_email) VALUES (?,?,?)";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, s.getSponsorName());
            ps.setString(2, s.getIndustry());
            ps.setString(3, s.getContactEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateSponsor(Sponsor s) {
        String sql = "UPDATE SPONSOR SET sponsor_name=?, industry=?, contact_email=? WHERE sponsor_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, s.getSponsorName());
            ps.setString(2, s.getIndustry());
            ps.setString(3, s.getContactEmail());
            ps.setInt(4, s.getSponsorId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteSponsor(int id) {
        String sql = "DELETE FROM SPONSOR WHERE sponsor_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ════════════════════════════════════════════════════════════════
    // SPONSORSHIP — CRUD
    // ════════════════════════════════════════════════════════════════

    public List<Sponsorship> getAllSponsorships() {
        List<Sponsorship> list = new ArrayList<>();
        String sql = "SELECT * FROM SPONSORSHIP ORDER BY sponsorship_id";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Sponsorship(
                    rs.getInt("sponsorship_id"), rs.getInt("sponsor_id"),
                    rs.getInt("tournament_id"), rs.getBigDecimal("amount_contributed"),
                    rs.getDate("contract_start").toLocalDate(),
                    rs.getDate("contract_end").toLocalDate()));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insertSponsorship(Sponsorship s) {
        String sql = "INSERT INTO SPONSORSHIP (sponsor_id, tournament_id, amount_contributed, contract_start, contract_end) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, s.getSponsorId());
            ps.setInt(2, s.getTournamentId());
            ps.setBigDecimal(3, s.getAmountContributed());
            ps.setDate(4, Date.valueOf(s.getContractStart()));
            ps.setDate(5, Date.valueOf(s.getContractEnd()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateSponsorship(Sponsorship s) {
        String sql = "UPDATE SPONSORSHIP SET sponsor_id=?, tournament_id=?, amount_contributed=?, contract_start=?, contract_end=? WHERE sponsorship_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, s.getSponsorId());
            ps.setInt(2, s.getTournamentId());
            ps.setBigDecimal(3, s.getAmountContributed());
            ps.setDate(4, Date.valueOf(s.getContractStart()));
            ps.setDate(5, Date.valueOf(s.getContractEnd()));
            ps.setInt(6, s.getSponsorshipId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteSponsorship(int id) {
        String sql = "DELETE FROM SPONSORSHIP WHERE sponsorship_id=?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ════════════════════════════════════════════════════════════════
    // VIEWS — Read Only (computed values, 3NF compliant)
    // ════════════════════════════════════════════════════════════════

    /** vw_player_earnings: player_id, username, real_name, total_earnings */
    public List<String[]> getPlayerEarnings() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT * FROM vw_player_earnings ORDER BY total_earnings DESC";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new String[]{
                    String.valueOf(rs.getInt("player_id")),
                    rs.getString("username"),
                    rs.getString("real_name"),
                    rs.getBigDecimal("total_earnings").toPlainString()
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    /** vw_team_earnings: team_id, team_name, country, total_prize_money */
    public List<String[]> getTeamEarnings() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT * FROM vw_team_earnings ORDER BY total_prize_money DESC";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new String[]{
                    String.valueOf(rs.getInt("team_id")),
                    rs.getString("team_name"),
                    rs.getString("country"),
                    rs.getBigDecimal("total_prize_money").toPlainString()
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    /** vw_global_leaderboard: team_name, country, total_earnings, podium_finishes */
    public List<String[]> getGlobalLeaderboard() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT * FROM vw_global_leaderboard ORDER BY total_earnings DESC";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("team_name"),
                    rs.getString("country"),
                    rs.getBigDecimal("total_earnings").toPlainString(),
                    String.valueOf(rs.getLong("podium_finishes"))
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // ════════════════════════════════════════════════════════════════
    // UTILITY — Custom query execution + row counts
    // ════════════════════════════════════════════════════════════════

    /** Execute a raw SELECT query and return results as List of String arrays */
    public List<String[]> executeQuery(String sql) throws SQLException {
        List<String[]> results = new ArrayList<>();
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();
            // First row is column headers
            String[] headers = new String[cols];
            for (int i = 1; i <= cols; i++) headers[i-1] = meta.getColumnLabel(i);
            results.add(headers);
            while (rs.next()) {
                String[] row = new String[cols];
                for (int i = 1; i <= cols; i++) {
                    Object val = rs.getObject(i);
                    row[i-1] = val != null ? val.toString() : "NULL";
                }
                results.add(row);
            }
        }
        return results;
    }

    /** Get row count for a specific table */
    public int getTableRowCount(String tableName) {
        // Sanitize table name to prevent SQL injection
        String safe = tableName.replaceAll("[^a-zA-Z0-9_]", "");
        String sql = "SELECT COUNT(*) FROM `" + safe + "`";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    /** Get total prize pool sum across all tournaments */
    public BigDecimal getTotalPrizePool() {
        String sql = "SELECT COALESCE(SUM(total_prize_pool), 0) FROM TOURNAMENT";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getBigDecimal(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return BigDecimal.ZERO;
    }
}
