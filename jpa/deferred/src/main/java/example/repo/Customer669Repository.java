package example.repo;

import example.model.Customer669;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer669Repository extends CrudRepository<Customer669, Long> {

	List<Customer669> findByLastName(String lastName);
}
