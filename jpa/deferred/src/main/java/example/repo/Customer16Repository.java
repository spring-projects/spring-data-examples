package example.repo;

import example.model.Customer16;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer16Repository extends CrudRepository<Customer16, Long> {

	List<Customer16> findByLastName(String lastName);
}
