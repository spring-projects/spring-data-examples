package example.repo;

import example.model.Customer437;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer437Repository extends CrudRepository<Customer437, Long> {

	List<Customer437> findByLastName(String lastName);
}
