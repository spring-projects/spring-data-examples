package example.repo;

import example.model.Customer196;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer196Repository extends CrudRepository<Customer196, Long> {

	List<Customer196> findByLastName(String lastName);
}
