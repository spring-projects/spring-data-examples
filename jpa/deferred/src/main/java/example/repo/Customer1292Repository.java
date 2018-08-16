package example.repo;

import example.model.Customer1292;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1292Repository extends CrudRepository<Customer1292, Long> {

	List<Customer1292> findByLastName(String lastName);
}
