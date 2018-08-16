package example.repo;

import example.model.Customer54;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer54Repository extends CrudRepository<Customer54, Long> {

	List<Customer54> findByLastName(String lastName);
}
