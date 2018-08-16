package example.repo;

import example.model.Customer672;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer672Repository extends CrudRepository<Customer672, Long> {

	List<Customer672> findByLastName(String lastName);
}
