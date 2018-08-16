package example.repo;

import example.model.Customer346;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer346Repository extends CrudRepository<Customer346, Long> {

	List<Customer346> findByLastName(String lastName);
}
