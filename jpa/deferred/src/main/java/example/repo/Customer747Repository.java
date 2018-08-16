package example.repo;

import example.model.Customer747;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer747Repository extends CrudRepository<Customer747, Long> {

	List<Customer747> findByLastName(String lastName);
}
