package example.repo;

import example.model.Customer780;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer780Repository extends CrudRepository<Customer780, Long> {

	List<Customer780> findByLastName(String lastName);
}
