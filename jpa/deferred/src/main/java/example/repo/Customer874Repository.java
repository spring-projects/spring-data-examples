package example.repo;

import example.model.Customer874;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer874Repository extends CrudRepository<Customer874, Long> {

	List<Customer874> findByLastName(String lastName);
}
