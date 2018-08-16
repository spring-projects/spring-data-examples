package example.repo;

import example.model.Customer205;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer205Repository extends CrudRepository<Customer205, Long> {

	List<Customer205> findByLastName(String lastName);
}
