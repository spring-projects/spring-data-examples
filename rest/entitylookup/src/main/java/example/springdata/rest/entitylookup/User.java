package example.springdata.rest.entitylookup;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Steve Rutherford
 */
@Getter
@Setter
@Entity
@Table(name = "app_user")
public class User {
	@Id
	private Long id;
	private String username;
	private String fullName;
}
