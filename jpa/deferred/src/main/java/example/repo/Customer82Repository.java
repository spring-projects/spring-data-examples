package example.repo;

import example.model.Customer82;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer82Repository extends CrudRepository<Customer82, Long> {

	List<Customer82> findByLastName(String lastName);
}
