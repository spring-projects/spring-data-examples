package example.repo;

import example.model.Customer900;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer900Repository extends CrudRepository<Customer900, Long> {

	List<Customer900> findByLastName(String lastName);
}
