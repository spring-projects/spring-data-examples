package example.repo;

import example.model.Customer1584;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1584Repository extends CrudRepository<Customer1584, Long> {

	List<Customer1584> findByLastName(String lastName);
}
