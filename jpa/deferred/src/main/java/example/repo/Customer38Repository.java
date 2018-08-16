package example.repo;

import example.model.Customer38;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer38Repository extends CrudRepository<Customer38, Long> {

	List<Customer38> findByLastName(String lastName);
}
