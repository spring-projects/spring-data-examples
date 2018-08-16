package example.repo;

import example.model.Customer961;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer961Repository extends CrudRepository<Customer961, Long> {

	List<Customer961> findByLastName(String lastName);
}
