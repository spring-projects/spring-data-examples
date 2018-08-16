package example.repo;

import example.model.Customer1868;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1868Repository extends CrudRepository<Customer1868, Long> {

	List<Customer1868> findByLastName(String lastName);
}
