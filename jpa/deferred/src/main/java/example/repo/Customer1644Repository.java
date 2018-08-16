package example.repo;

import example.model.Customer1644;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1644Repository extends CrudRepository<Customer1644, Long> {

	List<Customer1644> findByLastName(String lastName);
}
