package example.repo;

import example.model.Customer1521;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1521Repository extends CrudRepository<Customer1521, Long> {

	List<Customer1521> findByLastName(String lastName);
}
