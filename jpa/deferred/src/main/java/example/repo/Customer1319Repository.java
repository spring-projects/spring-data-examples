package example.repo;

import example.model.Customer1319;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1319Repository extends CrudRepository<Customer1319, Long> {

	List<Customer1319> findByLastName(String lastName);
}
