package example.repo;

import example.model.Customer645;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer645Repository extends CrudRepository<Customer645, Long> {

	List<Customer645> findByLastName(String lastName);
}
