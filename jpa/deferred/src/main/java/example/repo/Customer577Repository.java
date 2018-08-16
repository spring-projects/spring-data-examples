package example.repo;

import example.model.Customer577;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer577Repository extends CrudRepository<Customer577, Long> {

	List<Customer577> findByLastName(String lastName);
}
