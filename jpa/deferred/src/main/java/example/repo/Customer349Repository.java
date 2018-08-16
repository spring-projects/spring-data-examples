package example.repo;

import example.model.Customer349;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer349Repository extends CrudRepository<Customer349, Long> {

	List<Customer349> findByLastName(String lastName);
}
