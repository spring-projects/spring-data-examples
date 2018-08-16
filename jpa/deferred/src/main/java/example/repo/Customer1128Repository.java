package example.repo;

import example.model.Customer1128;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1128Repository extends CrudRepository<Customer1128, Long> {

	List<Customer1128> findByLastName(String lastName);
}
