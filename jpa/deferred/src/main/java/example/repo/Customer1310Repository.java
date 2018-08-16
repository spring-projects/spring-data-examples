package example.repo;

import example.model.Customer1310;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1310Repository extends CrudRepository<Customer1310, Long> {

	List<Customer1310> findByLastName(String lastName);
}
