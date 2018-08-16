package example.repo;

import example.model.Customer477;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer477Repository extends CrudRepository<Customer477, Long> {

	List<Customer477> findByLastName(String lastName);
}
