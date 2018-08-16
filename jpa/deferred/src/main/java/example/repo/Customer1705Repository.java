package example.repo;

import example.model.Customer1705;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1705Repository extends CrudRepository<Customer1705, Long> {

	List<Customer1705> findByLastName(String lastName);
}
