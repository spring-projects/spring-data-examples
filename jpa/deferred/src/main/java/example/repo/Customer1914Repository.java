package example.repo;

import example.model.Customer1914;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1914Repository extends CrudRepository<Customer1914, Long> {

	List<Customer1914> findByLastName(String lastName);
}
