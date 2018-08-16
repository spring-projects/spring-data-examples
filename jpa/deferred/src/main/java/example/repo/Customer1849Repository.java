package example.repo;

import example.model.Customer1849;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1849Repository extends CrudRepository<Customer1849, Long> {

	List<Customer1849> findByLastName(String lastName);
}
