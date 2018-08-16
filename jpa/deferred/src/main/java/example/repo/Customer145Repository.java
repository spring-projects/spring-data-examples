package example.repo;

import example.model.Customer145;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer145Repository extends CrudRepository<Customer145, Long> {

	List<Customer145> findByLastName(String lastName);
}
