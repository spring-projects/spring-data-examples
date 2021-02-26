package example.users;

import org.junit.Assert;
import org.junit.Test;

public class UserTest {
	public User setUser() {
		User usuario = new User();
		return usuario;	
	}
	
	@Test
	public void getDtoTest() {
		User usuario = setUser();
		Assert.assertNotNull(usuario);
	}

}
