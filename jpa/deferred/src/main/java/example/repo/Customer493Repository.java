package example.repo;

import example.model.Customer493;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer493Repository extends CrudRepository<Customer493, Long> {

	List<Customer493> findByLastName(String lastName);
}
