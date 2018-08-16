package example.repo;

import example.model.Customer1684;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1684Repository extends CrudRepository<Customer1684, Long> {

	List<Customer1684> findByLastName(String lastName);
}
