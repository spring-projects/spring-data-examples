package example.repo;

import example.model.Customer4;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer4Repository extends CrudRepository<Customer4, Long> {

	List<Customer4> findByLastName(String lastName);
}
