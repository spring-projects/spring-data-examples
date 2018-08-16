package example.repo;

import example.model.Customer336;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer336Repository extends CrudRepository<Customer336, Long> {

	List<Customer336> findByLastName(String lastName);
}
