package example.repo;

import example.model.Customer484;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer484Repository extends CrudRepository<Customer484, Long> {

	List<Customer484> findByLastName(String lastName);
}
