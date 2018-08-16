package example.repo;

import example.model.Customer1045;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1045Repository extends CrudRepository<Customer1045, Long> {

	List<Customer1045> findByLastName(String lastName);
}
