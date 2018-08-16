package example.repo;

import example.model.Customer1089;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1089Repository extends CrudRepository<Customer1089, Long> {

	List<Customer1089> findByLastName(String lastName);
}
