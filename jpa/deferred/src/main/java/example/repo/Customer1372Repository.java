package example.repo;

import example.model.Customer1372;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1372Repository extends CrudRepository<Customer1372, Long> {

	List<Customer1372> findByLastName(String lastName);
}
