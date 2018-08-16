package example.repo;

import example.model.Customer1935;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1935Repository extends CrudRepository<Customer1935, Long> {

	List<Customer1935> findByLastName(String lastName);
}
