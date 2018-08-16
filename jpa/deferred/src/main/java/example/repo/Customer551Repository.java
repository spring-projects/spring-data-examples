package example.repo;

import example.model.Customer551;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer551Repository extends CrudRepository<Customer551, Long> {

	List<Customer551> findByLastName(String lastName);
}
