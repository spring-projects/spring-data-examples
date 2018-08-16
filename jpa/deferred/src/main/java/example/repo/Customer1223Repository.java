package example.repo;

import example.model.Customer1223;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1223Repository extends CrudRepository<Customer1223, Long> {

	List<Customer1223> findByLastName(String lastName);
}
