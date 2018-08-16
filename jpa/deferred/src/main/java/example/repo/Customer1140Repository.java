package example.repo;

import example.model.Customer1140;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1140Repository extends CrudRepository<Customer1140, Long> {

	List<Customer1140> findByLastName(String lastName);
}
