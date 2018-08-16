package example.repo;

import example.model.Customer1286;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1286Repository extends CrudRepository<Customer1286, Long> {

	List<Customer1286> findByLastName(String lastName);
}
