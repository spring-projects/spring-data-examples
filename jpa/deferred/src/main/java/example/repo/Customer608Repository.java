package example.repo;

import example.model.Customer608;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer608Repository extends CrudRepository<Customer608, Long> {

	List<Customer608> findByLastName(String lastName);
}
