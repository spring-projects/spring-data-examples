package example.repo;

import example.model.Customer1238;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1238Repository extends CrudRepository<Customer1238, Long> {

	List<Customer1238> findByLastName(String lastName);
}
