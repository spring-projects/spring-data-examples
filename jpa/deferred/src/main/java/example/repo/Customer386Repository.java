package example.repo;

import example.model.Customer386;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer386Repository extends CrudRepository<Customer386, Long> {

	List<Customer386> findByLastName(String lastName);
}
