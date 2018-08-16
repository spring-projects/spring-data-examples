package example.repo;

import example.model.Customer1157;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1157Repository extends CrudRepository<Customer1157, Long> {

	List<Customer1157> findByLastName(String lastName);
}
