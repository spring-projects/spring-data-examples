package example.repo;

import example.model.Customer12;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer12Repository extends CrudRepository<Customer12, Long> {

	List<Customer12> findByLastName(String lastName);
}
