package example.repo;

import example.model.Customer731;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer731Repository extends CrudRepository<Customer731, Long> {

	List<Customer731> findByLastName(String lastName);
}
