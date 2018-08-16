package example.repo;

import example.model.Customer164;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer164Repository extends CrudRepository<Customer164, Long> {

	List<Customer164> findByLastName(String lastName);
}
