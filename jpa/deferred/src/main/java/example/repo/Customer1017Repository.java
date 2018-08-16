package example.repo;

import example.model.Customer1017;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1017Repository extends CrudRepository<Customer1017, Long> {

	List<Customer1017> findByLastName(String lastName);
}
