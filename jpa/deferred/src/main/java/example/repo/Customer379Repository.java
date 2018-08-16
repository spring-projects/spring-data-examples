package example.repo;

import example.model.Customer379;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer379Repository extends CrudRepository<Customer379, Long> {

	List<Customer379> findByLastName(String lastName);
}
