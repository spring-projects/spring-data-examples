package example.repo;

import example.model.Customer1043;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1043Repository extends CrudRepository<Customer1043, Long> {

	List<Customer1043> findByLastName(String lastName);
}
