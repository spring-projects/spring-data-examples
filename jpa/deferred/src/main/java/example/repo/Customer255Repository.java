package example.repo;

import example.model.Customer255;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer255Repository extends CrudRepository<Customer255, Long> {

	List<Customer255> findByLastName(String lastName);
}
