package example.repo;

import example.model.Customer445;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer445Repository extends CrudRepository<Customer445, Long> {

	List<Customer445> findByLastName(String lastName);
}
