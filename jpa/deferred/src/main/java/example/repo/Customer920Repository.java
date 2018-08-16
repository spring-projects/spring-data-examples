package example.repo;

import example.model.Customer920;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer920Repository extends CrudRepository<Customer920, Long> {

	List<Customer920> findByLastName(String lastName);
}
