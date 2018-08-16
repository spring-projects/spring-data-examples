package example.repo;

import example.model.Customer1794;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1794Repository extends CrudRepository<Customer1794, Long> {

	List<Customer1794> findByLastName(String lastName);
}
