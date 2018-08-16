package example.repo;

import example.model.Customer301;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer301Repository extends CrudRepository<Customer301, Long> {

	List<Customer301> findByLastName(String lastName);
}
