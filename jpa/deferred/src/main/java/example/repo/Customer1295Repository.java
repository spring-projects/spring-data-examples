package example.repo;

import example.model.Customer1295;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1295Repository extends CrudRepository<Customer1295, Long> {

	List<Customer1295> findByLastName(String lastName);
}
