package example.repo;

import example.model.Customer1150;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1150Repository extends CrudRepository<Customer1150, Long> {

	List<Customer1150> findByLastName(String lastName);
}
