package example.repo;

import example.model.Customer1649;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1649Repository extends CrudRepository<Customer1649, Long> {

	List<Customer1649> findByLastName(String lastName);
}
