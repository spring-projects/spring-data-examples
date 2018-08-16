package example.repo;

import example.model.Customer260;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer260Repository extends CrudRepository<Customer260, Long> {

	List<Customer260> findByLastName(String lastName);
}
