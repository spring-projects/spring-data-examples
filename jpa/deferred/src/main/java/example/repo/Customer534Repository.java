package example.repo;

import example.model.Customer534;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer534Repository extends CrudRepository<Customer534, Long> {

	List<Customer534> findByLastName(String lastName);
}
