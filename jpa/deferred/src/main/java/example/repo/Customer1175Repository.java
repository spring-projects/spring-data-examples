package example.repo;

import example.model.Customer1175;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1175Repository extends CrudRepository<Customer1175, Long> {

	List<Customer1175> findByLastName(String lastName);
}
