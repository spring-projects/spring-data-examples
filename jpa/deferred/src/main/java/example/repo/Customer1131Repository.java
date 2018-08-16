package example.repo;

import example.model.Customer1131;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1131Repository extends CrudRepository<Customer1131, Long> {

	List<Customer1131> findByLastName(String lastName);
}
