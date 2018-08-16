package example.repo;

import example.model.Customer267;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer267Repository extends CrudRepository<Customer267, Long> {

	List<Customer267> findByLastName(String lastName);
}
