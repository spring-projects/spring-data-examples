package example.springdata.jdbc.jooq;

import java.util.List;

public interface JooqRepository {
    List<Category> getCategoriesWithAgeGroup(AgeGroup ageGroup);
}
