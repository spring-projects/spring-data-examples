package example.repo;

import example.model.Customer1090;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1090Repository extends CrudRepository<Customer1090, Long> {

	List<Customer1090> findByLastName(String lastName);
}
