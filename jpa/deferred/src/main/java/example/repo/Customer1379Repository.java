package example.repo;

import example.model.Customer1379;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1379Repository extends CrudRepository<Customer1379, Long> {

	List<Customer1379> findByLastName(String lastName);
}
