package example.repo;

import example.model.Customer844;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer844Repository extends CrudRepository<Customer844, Long> {

	List<Customer844> findByLastName(String lastName);
}
