package example.repo;

import example.model.Customer1951;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1951Repository extends CrudRepository<Customer1951, Long> {

	List<Customer1951> findByLastName(String lastName);
}
