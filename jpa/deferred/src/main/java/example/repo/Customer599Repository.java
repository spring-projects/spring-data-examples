package example.repo;

import example.model.Customer599;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer599Repository extends CrudRepository<Customer599, Long> {

	List<Customer599> findByLastName(String lastName);
}
