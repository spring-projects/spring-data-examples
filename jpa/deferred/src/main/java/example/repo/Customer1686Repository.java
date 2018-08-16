package example.repo;

import example.model.Customer1686;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1686Repository extends CrudRepository<Customer1686, Long> {

	List<Customer1686> findByLastName(String lastName);
}
