package example.repo;

import example.model.Customer899;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer899Repository extends CrudRepository<Customer899, Long> {

	List<Customer899> findByLastName(String lastName);
}
