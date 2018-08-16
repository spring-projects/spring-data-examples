package example.repo;

import example.model.Customer184;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer184Repository extends CrudRepository<Customer184, Long> {

	List<Customer184> findByLastName(String lastName);
}
