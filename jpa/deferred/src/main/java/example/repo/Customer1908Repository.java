package example.repo;

import example.model.Customer1908;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1908Repository extends CrudRepository<Customer1908, Long> {

	List<Customer1908> findByLastName(String lastName);
}
