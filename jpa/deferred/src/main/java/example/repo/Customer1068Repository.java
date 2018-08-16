package example.repo;

import example.model.Customer1068;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1068Repository extends CrudRepository<Customer1068, Long> {

	List<Customer1068> findByLastName(String lastName);
}
