package example.repo;

import example.model.Customer315;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer315Repository extends CrudRepository<Customer315, Long> {

	List<Customer315> findByLastName(String lastName);
}
