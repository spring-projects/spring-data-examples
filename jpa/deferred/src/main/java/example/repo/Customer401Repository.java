package example.repo;

import example.model.Customer401;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer401Repository extends CrudRepository<Customer401, Long> {

	List<Customer401> findByLastName(String lastName);
}
