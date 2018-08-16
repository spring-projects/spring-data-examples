package example.repo;

import example.model.Customer99;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer99Repository extends CrudRepository<Customer99, Long> {

	List<Customer99> findByLastName(String lastName);
}
