package example.repo;

import example.model.Customer1206;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1206Repository extends CrudRepository<Customer1206, Long> {

	List<Customer1206> findByLastName(String lastName);
}
