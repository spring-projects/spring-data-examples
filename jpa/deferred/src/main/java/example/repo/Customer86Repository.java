package example.repo;

import example.model.Customer86;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer86Repository extends CrudRepository<Customer86, Long> {

	List<Customer86> findByLastName(String lastName);
}
