/**
 * 
 */
package example.springdata.aerospike.data;

import org.springframework.data.annotation.Id;

/**
 *
 *
 * @author Peter Milne
 * @author Jean Mercier
 *
 */
public class Person {

	@Id
	private String id;

	private String name;

	private int age;

	public Person(String id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
	}

}
