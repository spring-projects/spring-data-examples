package example.repo;

import example.model.Customer919;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer919Repository extends CrudRepository<Customer919, Long> {

	List<Customer919> findByLastName(String lastName);
}
