package example.repo;

import example.model.Customer137;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer137Repository extends CrudRepository<Customer137, Long> {

	List<Customer137> findByLastName(String lastName);
}
