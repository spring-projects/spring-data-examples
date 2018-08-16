package example.repo;

import example.model.Customer1553;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1553Repository extends CrudRepository<Customer1553, Long> {

	List<Customer1553> findByLastName(String lastName);
}
