package example.repo;

import example.model.Customer1256;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1256Repository extends CrudRepository<Customer1256, Long> {

	List<Customer1256> findByLastName(String lastName);
}
