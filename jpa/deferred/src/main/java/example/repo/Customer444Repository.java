package example.repo;

import example.model.Customer444;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer444Repository extends CrudRepository<Customer444, Long> {

	List<Customer444> findByLastName(String lastName);
}
