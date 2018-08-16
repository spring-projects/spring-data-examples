package example.repo;

import example.model.Customer1671;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1671Repository extends CrudRepository<Customer1671, Long> {

	List<Customer1671> findByLastName(String lastName);
}
