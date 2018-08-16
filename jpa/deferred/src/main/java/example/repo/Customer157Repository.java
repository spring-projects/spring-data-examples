package example.repo;

import example.model.Customer157;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer157Repository extends CrudRepository<Customer157, Long> {

	List<Customer157> findByLastName(String lastName);
}
