package example.repo;

import example.model.Customer603;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer603Repository extends CrudRepository<Customer603, Long> {

	List<Customer603> findByLastName(String lastName);
}
