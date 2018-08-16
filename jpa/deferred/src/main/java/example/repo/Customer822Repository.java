package example.repo;

import example.model.Customer822;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer822Repository extends CrudRepository<Customer822, Long> {

	List<Customer822> findByLastName(String lastName);
}
