package example.repo;

import example.model.Customer993;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer993Repository extends CrudRepository<Customer993, Long> {

	List<Customer993> findByLastName(String lastName);
}
