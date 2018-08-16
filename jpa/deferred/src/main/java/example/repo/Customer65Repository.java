package example.repo;

import example.model.Customer65;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer65Repository extends CrudRepository<Customer65, Long> {

	List<Customer65> findByLastName(String lastName);
}
