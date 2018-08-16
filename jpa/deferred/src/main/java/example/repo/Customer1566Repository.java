package example.repo;

import example.model.Customer1566;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1566Repository extends CrudRepository<Customer1566, Long> {

	List<Customer1566> findByLastName(String lastName);
}
