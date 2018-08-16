package example.repo;

import example.model.Customer1722;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1722Repository extends CrudRepository<Customer1722, Long> {

	List<Customer1722> findByLastName(String lastName);
}
