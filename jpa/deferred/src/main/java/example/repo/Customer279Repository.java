package example.repo;

import example.model.Customer279;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer279Repository extends CrudRepository<Customer279, Long> {

	List<Customer279> findByLastName(String lastName);
}
