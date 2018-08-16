package example.repo;

import example.model.Customer372;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer372Repository extends CrudRepository<Customer372, Long> {

	List<Customer372> findByLastName(String lastName);
}
