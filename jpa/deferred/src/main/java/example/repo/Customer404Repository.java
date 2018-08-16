package example.repo;

import example.model.Customer404;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer404Repository extends CrudRepository<Customer404, Long> {

	List<Customer404> findByLastName(String lastName);
}
