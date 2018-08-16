package example.repo;

import example.model.Customer978;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer978Repository extends CrudRepository<Customer978, Long> {

	List<Customer978> findByLastName(String lastName);
}
