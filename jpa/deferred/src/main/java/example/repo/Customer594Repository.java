package example.repo;

import example.model.Customer594;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer594Repository extends CrudRepository<Customer594, Long> {

	List<Customer594> findByLastName(String lastName);
}
