package example.repo;

import example.model.Customer330;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer330Repository extends CrudRepository<Customer330, Long> {

	List<Customer330> findByLastName(String lastName);
}
