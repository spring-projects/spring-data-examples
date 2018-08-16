package example.repo;

import example.model.Customer1137;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1137Repository extends CrudRepository<Customer1137, Long> {

	List<Customer1137> findByLastName(String lastName);
}
