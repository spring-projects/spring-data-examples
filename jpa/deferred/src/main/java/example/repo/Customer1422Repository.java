package example.repo;

import example.model.Customer1422;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1422Repository extends CrudRepository<Customer1422, Long> {

	List<Customer1422> findByLastName(String lastName);
}
