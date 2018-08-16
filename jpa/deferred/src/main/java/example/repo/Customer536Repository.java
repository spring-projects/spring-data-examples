package example.repo;

import example.model.Customer536;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer536Repository extends CrudRepository<Customer536, Long> {

	List<Customer536> findByLastName(String lastName);
}
