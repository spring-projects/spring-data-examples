package example.repo;

import example.model.Customer671;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer671Repository extends CrudRepository<Customer671, Long> {

	List<Customer671> findByLastName(String lastName);
}
