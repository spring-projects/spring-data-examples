package example.repo;

import example.model.Customer339;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer339Repository extends CrudRepository<Customer339, Long> {

	List<Customer339> findByLastName(String lastName);
}
