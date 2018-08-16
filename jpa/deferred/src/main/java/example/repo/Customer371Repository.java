package example.repo;

import example.model.Customer371;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer371Repository extends CrudRepository<Customer371, Long> {

	List<Customer371> findByLastName(String lastName);
}
