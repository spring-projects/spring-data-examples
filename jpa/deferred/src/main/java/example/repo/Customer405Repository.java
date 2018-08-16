package example.repo;

import example.model.Customer405;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer405Repository extends CrudRepository<Customer405, Long> {

	List<Customer405> findByLastName(String lastName);
}
