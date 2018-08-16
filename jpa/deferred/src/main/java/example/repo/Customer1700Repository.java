package example.repo;

import example.model.Customer1700;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1700Repository extends CrudRepository<Customer1700, Long> {

	List<Customer1700> findByLastName(String lastName);
}
