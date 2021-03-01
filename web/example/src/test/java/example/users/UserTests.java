package example.users;

import org.junit.Assert;
import org.junit.Test;

public class UserTests {
	public User setUser() {
		User usuario = new User();
		return usuario;	
	}
	
	@Test
	public void getDtoTests() {
		User usuario = setUser();
		Assert.assertNotNull(usuario);
	}

}
