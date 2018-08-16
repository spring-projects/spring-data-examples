package example.repo;

import example.model.Customer10;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer10Repository extends CrudRepository<Customer10, Long> {

	List<Customer10> findByLastName(String lastName);
}
