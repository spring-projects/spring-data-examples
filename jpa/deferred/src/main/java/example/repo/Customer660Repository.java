package example.repo;

import example.model.Customer660;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer660Repository extends CrudRepository<Customer660, Long> {

	List<Customer660> findByLastName(String lastName);
}
