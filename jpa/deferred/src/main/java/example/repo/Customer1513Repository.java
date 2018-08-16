package example.repo;

import example.model.Customer1513;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1513Repository extends CrudRepository<Customer1513, Long> {

	List<Customer1513> findByLastName(String lastName);
}
