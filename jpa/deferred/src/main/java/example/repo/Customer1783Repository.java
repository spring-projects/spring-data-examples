package example.repo;

import example.model.Customer1783;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1783Repository extends CrudRepository<Customer1783, Long> {

	List<Customer1783> findByLastName(String lastName);
}
