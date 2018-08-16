package example.repo;

import example.model.Customer1880;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1880Repository extends CrudRepository<Customer1880, Long> {

	List<Customer1880> findByLastName(String lastName);
}
