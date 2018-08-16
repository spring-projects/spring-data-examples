package example.repo;

import example.model.Customer1353;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1353Repository extends CrudRepository<Customer1353, Long> {

	List<Customer1353> findByLastName(String lastName);
}
