package example.repo;

import example.model.Customer648;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer648Repository extends CrudRepository<Customer648, Long> {

	List<Customer648> findByLastName(String lastName);
}
