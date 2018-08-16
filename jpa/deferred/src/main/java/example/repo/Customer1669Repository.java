package example.repo;

import example.model.Customer1669;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1669Repository extends CrudRepository<Customer1669, Long> {

	List<Customer1669> findByLastName(String lastName);
}
