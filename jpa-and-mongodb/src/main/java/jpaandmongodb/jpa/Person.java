package jpaandmongodb.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person {

	@Id @GeneratedValue
	private Long id;

	private String firstName;
	private String lastName;

	protected Person() {
	}

	public Person(String firstname, String lastName) {
		this();
		this.firstName = firstname;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return this.firstName + " " + this.lastName;
	}
}
