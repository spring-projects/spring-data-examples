package org.springframework.data.jpa.showcase.snippets;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.showcase.core.Account;
import org.springframework.data.jpa.showcase.core.QAccount;

import com.mysema.query.types.expr.BooleanExpression;

/**
 * Predicates for {@link Account}s.
 * 
 * @author Oliver Gierke
 */
public class AccountPredicates {

	private static QAccount $ = QAccount.account;

	public static BooleanExpression isExpired() {
		return expiresBefore(new LocalDate());
	}

	public static BooleanExpression expiresBefore(LocalDate date) {
		return $.expiryDate.before(date.toDateTimeAtStartOfDay().toDate());
	}
}
