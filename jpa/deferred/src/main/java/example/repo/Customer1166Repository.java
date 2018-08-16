package example.repo;

import example.model.Customer1166;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1166Repository extends CrudRepository<Customer1166, Long> {

	List<Customer1166> findByLastName(String lastName);
}
