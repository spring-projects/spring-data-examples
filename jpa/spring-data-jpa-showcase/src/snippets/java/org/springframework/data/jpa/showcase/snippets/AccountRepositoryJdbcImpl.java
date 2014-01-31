package org.springframework.data.jpa.showcase.snippets;

import org.joda.time.LocalDate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Oliver Gierke
 */
@Repository
class AccountRepositoryJdbcImpl implements AccountRepositoryCustom {

	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.showcase.snippets.AccountRepositoryCustom#removedExpiredAccounts(org.joda.time.LocalDate)
	 */
	@Override
	public void removedExpiredAccounts(LocalDate reference) {
		template.update("DELETE Account AS a WHERE a.expiryDate < ?", reference.toDateTimeAtStartOfDay().toDate());
	}
}
