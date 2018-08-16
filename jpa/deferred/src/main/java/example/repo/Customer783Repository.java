package example.repo;

import example.model.Customer783;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer783Repository extends CrudRepository<Customer783, Long> {

	List<Customer783> findByLastName(String lastName);
}
