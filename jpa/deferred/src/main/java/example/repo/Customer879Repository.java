package example.repo;

import example.model.Customer879;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer879Repository extends CrudRepository<Customer879, Long> {

	List<Customer879> findByLastName(String lastName);
}
