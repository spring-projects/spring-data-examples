package example.repo;

import example.model.Customer735;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer735Repository extends CrudRepository<Customer735, Long> {

	List<Customer735> findByLastName(String lastName);
}
