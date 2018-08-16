package example.repo;

import example.model.Customer266;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer266Repository extends CrudRepository<Customer266, Long> {

	List<Customer266> findByLastName(String lastName);
}
