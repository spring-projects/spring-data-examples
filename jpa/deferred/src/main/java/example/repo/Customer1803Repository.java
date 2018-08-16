package example.repo;

import example.model.Customer1803;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1803Repository extends CrudRepository<Customer1803, Long> {

	List<Customer1803> findByLastName(String lastName);
}
