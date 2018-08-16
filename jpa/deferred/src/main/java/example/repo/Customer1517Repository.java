package example.repo;

import example.model.Customer1517;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1517Repository extends CrudRepository<Customer1517, Long> {

	List<Customer1517> findByLastName(String lastName);
}
