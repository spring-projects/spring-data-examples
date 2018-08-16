package example.repo;

import example.model.Customer1050;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1050Repository extends CrudRepository<Customer1050, Long> {

	List<Customer1050> findByLastName(String lastName);
}
