package example.repo;

import example.model.Customer627;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer627Repository extends CrudRepository<Customer627, Long> {

	List<Customer627> findByLastName(String lastName);
}
