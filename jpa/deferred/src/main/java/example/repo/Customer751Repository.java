package example.repo;

import example.model.Customer751;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer751Repository extends CrudRepository<Customer751, Long> {

	List<Customer751> findByLastName(String lastName);
}
