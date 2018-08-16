package example.repo;

import example.model.Customer1349;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1349Repository extends CrudRepository<Customer1349, Long> {

	List<Customer1349> findByLastName(String lastName);
}
