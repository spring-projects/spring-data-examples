package example.repo;

import example.model.Customer294;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer294Repository extends CrudRepository<Customer294, Long> {

	List<Customer294> findByLastName(String lastName);
}
