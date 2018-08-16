package example.repo;

import example.model.Customer1305;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1305Repository extends CrudRepository<Customer1305, Long> {

	List<Customer1305> findByLastName(String lastName);
}
