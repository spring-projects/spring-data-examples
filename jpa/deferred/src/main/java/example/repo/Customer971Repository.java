package example.repo;

import example.model.Customer971;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer971Repository extends CrudRepository<Customer971, Long> {

	List<Customer971> findByLastName(String lastName);
}
