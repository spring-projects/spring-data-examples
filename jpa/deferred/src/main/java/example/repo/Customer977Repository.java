package example.repo;

import example.model.Customer977;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer977Repository extends CrudRepository<Customer977, Long> {

	List<Customer977> findByLastName(String lastName);
}
