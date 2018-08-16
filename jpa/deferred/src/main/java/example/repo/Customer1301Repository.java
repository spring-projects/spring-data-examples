package example.repo;

import example.model.Customer1301;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1301Repository extends CrudRepository<Customer1301, Long> {

	List<Customer1301> findByLastName(String lastName);
}
