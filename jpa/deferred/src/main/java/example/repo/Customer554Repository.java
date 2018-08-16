package example.repo;

import example.model.Customer554;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer554Repository extends CrudRepository<Customer554, Long> {

	List<Customer554> findByLastName(String lastName);
}
