/*
 * Copyright 2013-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.jpa.custom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

/**
 * Class with the implementation of the custom repository code. Uses JDBC in this case. For basic programatic setup see
 * {@link UserRepositoryImpl} for examples.
 * <p>
 * As you need to hand the instance a {@link javax.sql.DataSource} or
 * {@link org.springframework.jdbc.core.simple.SimpleJdbcTemplate} you manually need to declare it as Spring bean:
 * 
 * <pre>
 * &lt;jpa:repository base-package=&quot;com.acme.repository&quot; /&gt;
 * 
 * &lt;bean id=&quot;userRepositoryImpl&quot; class=&quot;...UserRepositoryJdbcImpl&quot;&gt;
 *   &lt;property name=&quot;dataSource&quot; ref=&quot;dataSource&quot; /&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * Using {@code userRepositoryImpl} will cause the repository instance get this bean injected for custom repository
 * logic as the default postfix for custom DAO instances is {@code Impl}.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
@Profile("jdbc")
@Component("userRepositoryImpl")
class UserRepositoryImplJdbc extends JdbcDaoSupport implements UserRepositoryCustom {

	private static final String COMPLICATED_SQL = "SELECT * FROM User";

	@Autowired
	public UserRepositoryImplJdbc(DataSource dataSource) {
		setDataSource(dataSource);
	}

	/*
	 * (non-Javadoc)
	 * @see example.springdata.jpa.UserRepositoryCustom#myCustomBatchOperation()
	 */
	public List<User> myCustomBatchOperation() {
		return getJdbcTemplate().query(COMPLICATED_SQL, new UserRowMapper());
	}

	private static class UserRowMapper implements RowMapper<User> {

		/*
		 * (non-Javadoc)
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 */
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {

			User user = new User(rs.getLong("id"));
			user.setUsername(rs.getString("username"));
			user.setLastname(rs.getString("lastname"));
			user.setFirstname(rs.getString("firstname"));

			return user;
		}
	}
}
