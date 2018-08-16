package example.repo;

import example.model.Customer295;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer295Repository extends CrudRepository<Customer295, Long> {

	List<Customer295> findByLastName(String lastName);
}
