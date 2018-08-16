package example.repo;

import example.model.Customer727;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer727Repository extends CrudRepository<Customer727, Long> {

	List<Customer727> findByLastName(String lastName);
}
