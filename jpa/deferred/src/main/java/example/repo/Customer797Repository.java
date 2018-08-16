package example.repo;

import example.model.Customer797;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer797Repository extends CrudRepository<Customer797, Long> {

	List<Customer797> findByLastName(String lastName);
}
