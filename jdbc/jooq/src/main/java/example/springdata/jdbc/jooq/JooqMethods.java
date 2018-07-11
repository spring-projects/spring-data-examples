package example.springdata.jdbc.jooq;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static example.springdata.jdbc.basics.simpleentity.domain.tables.Category.CATEGORY;

/**
 * Implementations for custom repository access using jOOQ.
 *
 * @author Florian Lüdiger
 */
@Component
public class JooqMethods {

    private final JdbcTemplate jdbcTemplate;

    private final DSLContext dslContext;

    public JooqMethods(JdbcTemplate jdbcTemplate, DSLContext dslContext) {
        this.jdbcTemplate = jdbcTemplate;
        this.dslContext = dslContext;
    }

    List<Category> getCategoriesWithAgeGroup(AgeGroup ageGroup) {
        Result<Record> res = this.dslContext.select().from(CATEGORY).where(CATEGORY.AGE_GROUP.equal(ageGroup.name())).fetch();
        List<Category> categoryList = new ArrayList<Category>();
        for (Record result : res) {
            categoryList.add(categoryFromRecord(result));
        }
        return categoryList;
    }

    Category categoryFromRecord(Record record) {
        String name = record.getValue(CATEGORY.NAME);
        String description = record.getValue(CATEGORY.DESCRIPTION);
        String ageGroup = record.getValue(CATEGORY.AGE_GROUP);
        return new Category(name, description, AgeGroup.valueOf(ageGroup));
    }
}
