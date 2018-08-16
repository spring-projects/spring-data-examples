package example.repo;

import example.model.Customer706;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer706Repository extends CrudRepository<Customer706, Long> {

	List<Customer706> findByLastName(String lastName);
}
