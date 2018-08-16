package example.repo;

import example.model.Customer1330;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1330Repository extends CrudRepository<Customer1330, Long> {

	List<Customer1330> findByLastName(String lastName);
}
