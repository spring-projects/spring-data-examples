package example.repo;

import example.model.Customer877;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer877Repository extends CrudRepository<Customer877, Long> {

	List<Customer877> findByLastName(String lastName);
}
