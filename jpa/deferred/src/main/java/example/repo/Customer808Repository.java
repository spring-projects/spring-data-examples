package example.repo;

import example.model.Customer808;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer808Repository extends CrudRepository<Customer808, Long> {

	List<Customer808> findByLastName(String lastName);
}
