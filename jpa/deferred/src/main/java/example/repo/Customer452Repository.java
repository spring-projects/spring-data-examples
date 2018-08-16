package example.repo;

import example.model.Customer452;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer452Repository extends CrudRepository<Customer452, Long> {

	List<Customer452> findByLastName(String lastName);
}
