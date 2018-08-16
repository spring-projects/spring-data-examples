package example.repo;

import example.model.Customer609;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer609Repository extends CrudRepository<Customer609, Long> {

	List<Customer609> findByLastName(String lastName);
}
