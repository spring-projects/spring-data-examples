package example.repo;

import example.model.Customer535;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer535Repository extends CrudRepository<Customer535, Long> {

	List<Customer535> findByLastName(String lastName);
}
