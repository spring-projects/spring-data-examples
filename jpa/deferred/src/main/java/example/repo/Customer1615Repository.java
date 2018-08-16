package example.repo;

import example.model.Customer1615;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1615Repository extends CrudRepository<Customer1615, Long> {

	List<Customer1615> findByLastName(String lastName);
}
