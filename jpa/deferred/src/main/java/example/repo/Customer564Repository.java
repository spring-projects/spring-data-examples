package example.repo;

import example.model.Customer564;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer564Repository extends CrudRepository<Customer564, Long> {

	List<Customer564> findByLastName(String lastName);
}
