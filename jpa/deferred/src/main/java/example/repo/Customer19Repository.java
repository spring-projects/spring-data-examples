package example.repo;

import example.model.Customer19;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer19Repository extends CrudRepository<Customer19, Long> {

	List<Customer19> findByLastName(String lastName);
}
