package example.repo;

import example.model.Customer793;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer793Repository extends CrudRepository<Customer793, Long> {

	List<Customer793> findByLastName(String lastName);
}
