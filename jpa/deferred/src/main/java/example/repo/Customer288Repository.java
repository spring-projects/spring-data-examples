package example.repo;

import example.model.Customer288;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer288Repository extends CrudRepository<Customer288, Long> {

	List<Customer288> findByLastName(String lastName);
}
