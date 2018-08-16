package example.repo;

import example.model.Customer560;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer560Repository extends CrudRepository<Customer560, Long> {

	List<Customer560> findByLastName(String lastName);
}
