package example.repo;

import example.model.Customer1101;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1101Repository extends CrudRepository<Customer1101, Long> {

	List<Customer1101> findByLastName(String lastName);
}
