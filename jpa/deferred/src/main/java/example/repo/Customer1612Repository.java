package example.repo;

import example.model.Customer1612;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1612Repository extends CrudRepository<Customer1612, Long> {

	List<Customer1612> findByLastName(String lastName);
}
