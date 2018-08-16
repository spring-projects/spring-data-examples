package example.repo;

import example.model.Customer890;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer890Repository extends CrudRepository<Customer890, Long> {

	List<Customer890> findByLastName(String lastName);
}
