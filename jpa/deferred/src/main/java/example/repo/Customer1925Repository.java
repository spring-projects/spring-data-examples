package example.repo;

import example.model.Customer1925;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1925Repository extends CrudRepository<Customer1925, Long> {

	List<Customer1925> findByLastName(String lastName);
}
