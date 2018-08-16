package example.repo;

import example.model.Customer298;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer298Repository extends CrudRepository<Customer298, Long> {

	List<Customer298> findByLastName(String lastName);
}
