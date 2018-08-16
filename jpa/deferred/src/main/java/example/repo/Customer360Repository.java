package example.repo;

import example.model.Customer360;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer360Repository extends CrudRepository<Customer360, Long> {

	List<Customer360> findByLastName(String lastName);
}
