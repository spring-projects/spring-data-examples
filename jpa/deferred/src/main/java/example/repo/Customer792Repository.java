package example.repo;

import example.model.Customer792;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer792Repository extends CrudRepository<Customer792, Long> {

	List<Customer792> findByLastName(String lastName);
}
