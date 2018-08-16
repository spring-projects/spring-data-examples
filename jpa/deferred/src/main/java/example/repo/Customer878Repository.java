package example.repo;

import example.model.Customer878;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer878Repository extends CrudRepository<Customer878, Long> {

	List<Customer878> findByLastName(String lastName);
}
