package example.repo;

import example.model.Customer1247;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1247Repository extends CrudRepository<Customer1247, Long> {

	List<Customer1247> findByLastName(String lastName);
}
