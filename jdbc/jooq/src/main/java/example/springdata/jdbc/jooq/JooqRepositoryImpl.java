package example.springdata.jdbc.jooq;

import static example.springdata.jdbc.basics.simpleentity.domain.tables.Category.*;

import java.util.List;

import org.jooq.DSLContext;

/**
 * Implementations for custom repository access using jOOQ.
 *
 * @author Florian Lüdiger
 */
public record JooqRepositoryImpl(DSLContext dslContext) implements JooqRepository {

	public List<Category> getCategoriesWithAgeGroup(AgeGroup ageGroup) {
		return this.dslContext.select().from(CATEGORY).where(CATEGORY.AGE_GROUP.equal(ageGroup.name()))
				.fetchInto(Category.class);
	}
}
