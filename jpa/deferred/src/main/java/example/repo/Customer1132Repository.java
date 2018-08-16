package example.repo;

import example.model.Customer1132;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1132Repository extends CrudRepository<Customer1132, Long> {

	List<Customer1132> findByLastName(String lastName);
}
