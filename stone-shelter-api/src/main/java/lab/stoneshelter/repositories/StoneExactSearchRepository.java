package lab.stoneshelter.repositories;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lab.stoneshelter.criteria.StoneExactSearchCriteria;
import lab.stoneshelter.entities.StoneEntity;
import lab.stoneshelter.entities.StoneExactSearchResultEntity;
import lab.stoneshelter.enums.StoneType;
import lab.stoneshelter.enums.StoneSize;
import lab.stoneshelter.enums.AdoptionStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true, timeout = 5)
public class StoneExactSearchRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<StoneEntity> mapper = (row, index) -> {
        StoneEntity stone = new StoneEntity(); stone.setId(row.getLong("id")); stone.setName(row.getString("name"));
        stone.setStoneType(StoneType.valueOf(row.getString("stone_type"))); stone.setStoneSize(StoneSize.valueOf(row.getString("size")));
        stone.setAdoptionStatus(AdoptionStatus.valueOf(row.getString("adoption_status"))); stone.setAdmissionDate(row.getTimestamp("admission_date").toInstant());
        return stone;
    };
    public StoneExactSearchRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }
    public Optional<StoneEntity> findById(long id) {
        return jdbcTemplate.query("SELECT id,name,stone_type,size,adoption_status,admission_date FROM stone WHERE id=? AND adoption_status='AVAILABLE'", mapper, id).stream().findFirst();
    }
    @Transactional(readOnly = true, isolation = org.springframework.transaction.annotation.Isolation.REPEATABLE_READ, timeout = 5)
    public StoneExactSearchResultEntity search(StoneExactSearchCriteria criteria) {
        if (!criteria.hasNarrowing()) throw new IllegalArgumentException("Unfiltered exact search is forbidden");
        StringBuilder where = new StringBuilder(" WHERE adoption_status='AVAILABLE'"); List<Object> values = new ArrayList<>();
        in(where, values, "stone_type", criteria.getStoneTypes()); in(where, values, "size", criteria.getStoneSizes());
        in(where, values, "adoption_status", criteria.getAvailability());
        if (criteria.getStoneId() != null) { where.append(" AND id=?"); values.add(criteria.getStoneId()); }
        if (!criteria.getAdmissionDateRanges().isEmpty()) {
            where.append(" AND ("); boolean first = true;
            for (var range : criteria.getAdmissionDateRanges()) {
                if (!first) where.append(" OR "); first = false;
                where.append("(admission_date>=? AND admission_date<?)");
                values.add(Timestamp.from(range.startInclusive())); values.add(Timestamp.from(range.endExclusive()));
            }
            where.append(')');
        }
        StoneExactSearchResultEntity result = new StoneExactSearchResultEntity(); result.setCriteria(criteria);
        result.setTotal(jdbcTemplate.queryForObject("SELECT count(*) FROM stone" + where, Long.class, values.toArray()));
        result.setStones(jdbcTemplate.query("SELECT id,name,stone_type,size,adoption_status,admission_date FROM stone" + where
                + " ORDER BY admission_date DESC,id ASC LIMIT 5", mapper, values.toArray()));
        return result;
    }
    private void in(StringBuilder where, List<Object> parameters, String column, List<? extends Enum<?>> values) {
        if (values.isEmpty()) return;
        where.append(" AND ").append(column).append(" IN (").append(String.join(",", java.util.Collections.nCopies(values.size(), "?"))).append(')');
        values.forEach(value -> parameters.add(value.name()));
    }
}
