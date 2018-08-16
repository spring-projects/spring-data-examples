package example.repo;

import example.model.Customer774;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer774Repository extends CrudRepository<Customer774, Long> {

	List<Customer774> findByLastName(String lastName);
}
