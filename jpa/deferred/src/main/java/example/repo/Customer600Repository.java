package example.repo;

import example.model.Customer600;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer600Repository extends CrudRepository<Customer600, Long> {

	List<Customer600> findByLastName(String lastName);
}
