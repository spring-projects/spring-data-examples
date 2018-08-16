package example.repo;

import example.model.Customer1126;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1126Repository extends CrudRepository<Customer1126, Long> {

	List<Customer1126> findByLastName(String lastName);
}
