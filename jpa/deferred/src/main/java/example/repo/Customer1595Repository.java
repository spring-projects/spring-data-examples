package example.repo;

import example.model.Customer1595;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1595Repository extends CrudRepository<Customer1595, Long> {

	List<Customer1595> findByLastName(String lastName);
}
