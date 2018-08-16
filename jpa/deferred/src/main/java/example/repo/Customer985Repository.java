package example.repo;

import example.model.Customer985;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer985Repository extends CrudRepository<Customer985, Long> {

	List<Customer985> findByLastName(String lastName);
}
