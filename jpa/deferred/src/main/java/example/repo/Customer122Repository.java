package example.repo;

import example.model.Customer122;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer122Repository extends CrudRepository<Customer122, Long> {

	List<Customer122> findByLastName(String lastName);
}
