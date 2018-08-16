package example.repo;

import example.model.Customer1738;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1738Repository extends CrudRepository<Customer1738, Long> {

	List<Customer1738> findByLastName(String lastName);
}
