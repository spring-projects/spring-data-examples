package example.repo;

import example.model.Customer1086;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1086Repository extends CrudRepository<Customer1086, Long> {

	List<Customer1086> findByLastName(String lastName);
}
