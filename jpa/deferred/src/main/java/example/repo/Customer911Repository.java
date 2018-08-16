package example.repo;

import example.model.Customer911;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer911Repository extends CrudRepository<Customer911, Long> {

	List<Customer911> findByLastName(String lastName);
}
