package example.repo;

import example.model.Customer242;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer242Repository extends CrudRepository<Customer242, Long> {

	List<Customer242> findByLastName(String lastName);
}
