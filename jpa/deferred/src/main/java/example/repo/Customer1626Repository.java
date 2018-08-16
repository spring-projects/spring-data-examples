package example.repo;

import example.model.Customer1626;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1626Repository extends CrudRepository<Customer1626, Long> {

	List<Customer1626> findByLastName(String lastName);
}
