package example.repo;

import example.model.Customer1782;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1782Repository extends CrudRepository<Customer1782, Long> {

	List<Customer1782> findByLastName(String lastName);
}
