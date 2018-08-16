package example.repo;

import example.model.Customer1767;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1767Repository extends CrudRepository<Customer1767, Long> {

	List<Customer1767> findByLastName(String lastName);
}
