package example.repo;

import example.model.Customer591;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer591Repository extends CrudRepository<Customer591, Long> {

	List<Customer591> findByLastName(String lastName);
}
