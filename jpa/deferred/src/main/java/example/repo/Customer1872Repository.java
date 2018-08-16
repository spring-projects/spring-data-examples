package example.repo;

import example.model.Customer1872;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1872Repository extends CrudRepository<Customer1872, Long> {

	List<Customer1872> findByLastName(String lastName);
}
