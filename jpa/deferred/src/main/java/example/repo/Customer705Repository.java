package example.repo;

import example.model.Customer705;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer705Repository extends CrudRepository<Customer705, Long> {

	List<Customer705> findByLastName(String lastName);
}
