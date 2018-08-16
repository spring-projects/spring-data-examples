package example.repo;

import example.model.Customer619;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer619Repository extends CrudRepository<Customer619, Long> {

	List<Customer619> findByLastName(String lastName);
}
