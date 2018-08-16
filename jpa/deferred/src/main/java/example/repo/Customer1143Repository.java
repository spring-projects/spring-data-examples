package example.repo;

import example.model.Customer1143;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1143Repository extends CrudRepository<Customer1143, Long> {

	List<Customer1143> findByLastName(String lastName);
}
