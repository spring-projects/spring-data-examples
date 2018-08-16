package example.repo;

import example.model.Customer1680;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1680Repository extends CrudRepository<Customer1680, Long> {

	List<Customer1680> findByLastName(String lastName);
}
