package example.repo;

import example.model.Customer209;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer209Repository extends CrudRepository<Customer209, Long> {

	List<Customer209> findByLastName(String lastName);
}
