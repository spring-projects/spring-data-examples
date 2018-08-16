package example.repo;

import example.model.Customer574;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer574Repository extends CrudRepository<Customer574, Long> {

	List<Customer574> findByLastName(String lastName);
}
