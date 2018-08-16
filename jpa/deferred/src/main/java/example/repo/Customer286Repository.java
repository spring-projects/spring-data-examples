package example.repo;

import example.model.Customer286;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer286Repository extends CrudRepository<Customer286, Long> {

	List<Customer286> findByLastName(String lastName);
}
