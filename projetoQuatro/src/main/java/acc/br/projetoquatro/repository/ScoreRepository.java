package acc.br.projetoquatro.repository;

import acc.br.projetoquatro.model.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ScoreRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Score findFirst() {
        try {
            List<Score> scores = jdbcTemplate.query("SELECT * FROM SCORE LIMIT 1", new ScoreRowMapper());
            return scores.isEmpty() ? null : scores.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public Score save(Score score) {
        if (score.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO SCORE (vitorias, derrotas, empates) VALUES (?, ?, ?)", 
                    Statement.RETURN_GENERATED_KEYS
                );
                ps.setInt(1, score.getVitorias());
                ps.setInt(2, score.getDerrotas());
                ps.setInt(3, score.getEmpates());
                return ps;
            }, keyHolder);
            
            score.setId(keyHolder.getKey().longValue());
        } else {
            jdbcTemplate.update("UPDATE SCORE SET vitorias = ?, derrotas = ?, empates = ? WHERE id = ?", 
                score.getVitorias(), score.getDerrotas(), score.getEmpates(), score.getId());
        }
        return score;
    }

    private static class ScoreRowMapper implements RowMapper<Score> {
        @Override
        public Score mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            Score score = new Score();
            score.setId(rs.getLong("id"));
            score.setVitorias(rs.getInt("vitorias"));
            score.setDerrotas(rs.getInt("derrotas"));
            score.setEmpates(rs.getInt("empates"));
            return score;
        }
    }
}