package example.repo;

import example.model.Customer238;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer238Repository extends CrudRepository<Customer238, Long> {

	List<Customer238> findByLastName(String lastName);
}
