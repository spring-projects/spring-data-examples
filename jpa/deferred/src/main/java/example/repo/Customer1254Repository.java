package example.repo;

import example.model.Customer1254;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1254Repository extends CrudRepository<Customer1254, Long> {

	List<Customer1254> findByLastName(String lastName);
}
