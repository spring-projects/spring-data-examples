package example.repo;

import example.model.Customer427;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer427Repository extends CrudRepository<Customer427, Long> {

	List<Customer427> findByLastName(String lastName);
}
