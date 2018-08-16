package example.repo;

import example.model.Customer1599;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1599Repository extends CrudRepository<Customer1599, Long> {

	List<Customer1599> findByLastName(String lastName);
}
