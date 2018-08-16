package example.repo;

import example.model.Customer248;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer248Repository extends CrudRepository<Customer248, Long> {

	List<Customer248> findByLastName(String lastName);
}
