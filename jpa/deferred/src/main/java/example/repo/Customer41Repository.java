package example.repo;

import example.model.Customer41;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer41Repository extends CrudRepository<Customer41, Long> {

	List<Customer41> findByLastName(String lastName);
}
