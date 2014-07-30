package jpaandmongodb.mongodb;

import org.springframework.data.annotation.Id;

public class Treasure {

	@Id
	private String id;

	private String name;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return this.name + " (" + this.description + ")";
	}
}
