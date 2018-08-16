package example.repo;

import example.model.Customer1229;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1229Repository extends CrudRepository<Customer1229, Long> {

	List<Customer1229> findByLastName(String lastName);
}
