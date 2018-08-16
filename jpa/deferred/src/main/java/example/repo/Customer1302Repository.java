package example.repo;

import example.model.Customer1302;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1302Repository extends CrudRepository<Customer1302, Long> {

	List<Customer1302> findByLastName(String lastName);
}
