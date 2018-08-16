package example.repo;

import example.model.Customer748;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer748Repository extends CrudRepository<Customer748, Long> {

	List<Customer748> findByLastName(String lastName);
}
