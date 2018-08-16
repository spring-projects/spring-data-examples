package example.repo;

import example.model.Customer762;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer762Repository extends CrudRepository<Customer762, Long> {

	List<Customer762> findByLastName(String lastName);
}
