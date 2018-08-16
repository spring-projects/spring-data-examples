package example.repo;

import example.model.Customer166;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer166Repository extends CrudRepository<Customer166, Long> {

	List<Customer166> findByLastName(String lastName);
}
