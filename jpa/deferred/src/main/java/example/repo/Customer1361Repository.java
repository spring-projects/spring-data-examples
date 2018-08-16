package example.repo;

import example.model.Customer1361;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1361Repository extends CrudRepository<Customer1361, Long> {

	List<Customer1361> findByLastName(String lastName);
}
