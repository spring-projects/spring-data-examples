package example.repo;

import example.model.Customer216;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer216Repository extends CrudRepository<Customer216, Long> {

	List<Customer216> findByLastName(String lastName);
}
