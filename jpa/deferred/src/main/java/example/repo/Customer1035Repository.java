package example.repo;

import example.model.Customer1035;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1035Repository extends CrudRepository<Customer1035, Long> {

	List<Customer1035> findByLastName(String lastName);
}
