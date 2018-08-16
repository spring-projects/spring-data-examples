package example.repo;

import example.model.Customer1795;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1795Repository extends CrudRepository<Customer1795, Long> {

	List<Customer1795> findByLastName(String lastName);
}
