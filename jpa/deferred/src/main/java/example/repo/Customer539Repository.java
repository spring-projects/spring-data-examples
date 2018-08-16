package example.repo;

import example.model.Customer539;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer539Repository extends CrudRepository<Customer539, Long> {

	List<Customer539> findByLastName(String lastName);
}
