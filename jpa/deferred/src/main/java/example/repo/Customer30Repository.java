package example.repo;

import example.model.Customer30;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer30Repository extends CrudRepository<Customer30, Long> {

	List<Customer30> findByLastName(String lastName);
}
