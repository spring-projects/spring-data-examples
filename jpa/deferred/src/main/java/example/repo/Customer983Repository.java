package example.repo;

import example.model.Customer983;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer983Repository extends CrudRepository<Customer983, Long> {

	List<Customer983> findByLastName(String lastName);
}
