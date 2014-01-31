package org.springframework.data.jpa.showcase.snippets;

import org.joda.time.LocalDate;

/**
 * @author Oliver Gierke
 */
interface AccountRepositoryCustom {

	void removedExpiredAccounts(LocalDate reference);
}
