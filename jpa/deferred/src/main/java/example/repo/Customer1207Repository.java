package example.repo;

import example.model.Customer1207;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1207Repository extends CrudRepository<Customer1207, Long> {

	List<Customer1207> findByLastName(String lastName);
}
