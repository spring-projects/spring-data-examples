package example.repo;

import example.model.Customer1167;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1167Repository extends CrudRepository<Customer1167, Long> {

	List<Customer1167> findByLastName(String lastName);
}
