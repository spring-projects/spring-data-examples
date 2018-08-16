package example.repo;

import example.model.Customer1735;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1735Repository extends CrudRepository<Customer1735, Long> {

	List<Customer1735> findByLastName(String lastName);
}
