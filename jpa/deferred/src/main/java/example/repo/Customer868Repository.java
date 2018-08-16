package example.repo;

import example.model.Customer868;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer868Repository extends CrudRepository<Customer868, Long> {

	List<Customer868> findByLastName(String lastName);
}
