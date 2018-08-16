package example.repo;

import example.model.Customer959;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer959Repository extends CrudRepository<Customer959, Long> {

	List<Customer959> findByLastName(String lastName);
}
