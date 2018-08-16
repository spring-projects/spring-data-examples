package example.repo;

import example.model.Customer120;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer120Repository extends CrudRepository<Customer120, Long> {

	List<Customer120> findByLastName(String lastName);
}
