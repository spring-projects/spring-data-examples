package example.repo;

import example.model.Customer532;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer532Repository extends CrudRepository<Customer532, Long> {

	List<Customer532> findByLastName(String lastName);
}
