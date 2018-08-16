package example.repo;

import example.model.Customer1864;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1864Repository extends CrudRepository<Customer1864, Long> {

	List<Customer1864> findByLastName(String lastName);
}
