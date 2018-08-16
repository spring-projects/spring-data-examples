package example.repo;

import example.model.Customer1851;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1851Repository extends CrudRepository<Customer1851, Long> {

	List<Customer1851> findByLastName(String lastName);
}
