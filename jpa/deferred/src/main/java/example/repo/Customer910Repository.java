package example.repo;

import example.model.Customer910;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer910Repository extends CrudRepository<Customer910, Long> {

	List<Customer910> findByLastName(String lastName);
}
