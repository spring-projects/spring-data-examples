package example.springdata.jdbc.jooq;

import java.util.List;

/**
 * @author Florian Lüdiger
 */
public interface JooqRepository {
	List<Category> getCategoriesWithAgeGroup(AgeGroup ageGroup);
}
