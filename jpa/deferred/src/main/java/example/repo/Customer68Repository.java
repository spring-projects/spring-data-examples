package example.repo;

import example.model.Customer68;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer68Repository extends CrudRepository<Customer68, Long> {

	List<Customer68> findByLastName(String lastName);
}
