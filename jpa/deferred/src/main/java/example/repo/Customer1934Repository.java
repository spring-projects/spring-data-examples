package example.repo;

import example.model.Customer1934;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1934Repository extends CrudRepository<Customer1934, Long> {

	List<Customer1934> findByLastName(String lastName);
}
