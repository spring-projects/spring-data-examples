package example.repo;

import example.model.Customer745;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer745Repository extends CrudRepository<Customer745, Long> {

	List<Customer745> findByLastName(String lastName);
}
