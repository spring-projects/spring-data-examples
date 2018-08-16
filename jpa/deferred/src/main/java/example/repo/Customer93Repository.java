package example.repo;

import example.model.Customer93;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer93Repository extends CrudRepository<Customer93, Long> {

	List<Customer93> findByLastName(String lastName);
}
