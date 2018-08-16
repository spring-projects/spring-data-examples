package example.repo;

import example.model.Customer1079;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1079Repository extends CrudRepository<Customer1079, Long> {

	List<Customer1079> findByLastName(String lastName);
}
