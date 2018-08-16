package example.repo;

import example.model.Customer511;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer511Repository extends CrudRepository<Customer511, Long> {

	List<Customer511> findByLastName(String lastName);
}
