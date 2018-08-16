package example.repo;

import example.model.Customer690;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer690Repository extends CrudRepository<Customer690, Long> {

	List<Customer690> findByLastName(String lastName);
}
