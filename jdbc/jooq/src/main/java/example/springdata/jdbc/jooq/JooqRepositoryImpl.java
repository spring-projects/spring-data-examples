package example.springdata.jdbc.jooq;

import org.jooq.DSLContext;

import java.util.List;

import static example.springdata.jdbc.basics.simpleentity.domain.tables.Category.CATEGORY;

/**
 * Implementations for custom repository access using jOOQ.
 *
 * @author Florian Lüdiger
 */
public class JooqRepositoryImpl implements JooqRepository {

    private final DSLContext dslContext;

    public JooqRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<Category> getCategoriesWithAgeGroup(AgeGroup ageGroup) {
        return this.dslContext
                .select()
                .from(CATEGORY)
                .where(CATEGORY.AGE_GROUP.equal(ageGroup.name()))
                .fetchInto(Category.class);
    }
}
