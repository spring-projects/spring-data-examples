package example.repo;

import example.model.Customer1610;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1610Repository extends CrudRepository<Customer1610, Long> {

	List<Customer1610> findByLastName(String lastName);
}
