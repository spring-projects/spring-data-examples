package example.repo;

import example.model.Customer320;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer320Repository extends CrudRepository<Customer320, Long> {

	List<Customer320> findByLastName(String lastName);
}
