package example.repo;

import example.model.Customer1995;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1995Repository extends CrudRepository<Customer1995, Long> {

	List<Customer1995> findByLastName(String lastName);
}
