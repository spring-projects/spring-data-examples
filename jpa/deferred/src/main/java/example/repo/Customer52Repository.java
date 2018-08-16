package example.repo;

import example.model.Customer52;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer52Repository extends CrudRepository<Customer52, Long> {

	List<Customer52> findByLastName(String lastName);
}
