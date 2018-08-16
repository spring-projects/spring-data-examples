package example.repo;

import example.model.Customer39;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer39Repository extends CrudRepository<Customer39, Long> {

	List<Customer39> findByLastName(String lastName);
}
