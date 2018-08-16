package example.repo;

import example.model.Customer680;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer680Repository extends CrudRepository<Customer680, Long> {

	List<Customer680> findByLastName(String lastName);
}
