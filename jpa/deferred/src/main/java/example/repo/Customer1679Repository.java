package example.repo;

import example.model.Customer1679;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1679Repository extends CrudRepository<Customer1679, Long> {

	List<Customer1679> findByLastName(String lastName);
}
