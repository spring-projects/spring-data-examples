package example.repo;

import example.model.Customer757;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer757Repository extends CrudRepository<Customer757, Long> {

	List<Customer757> findByLastName(String lastName);
}
