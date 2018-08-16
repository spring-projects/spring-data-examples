package example.repo;

import example.model.Customer490;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer490Repository extends CrudRepository<Customer490, Long> {

	List<Customer490> findByLastName(String lastName);
}
