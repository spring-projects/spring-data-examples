package example.repo;

import example.model.Customer1285;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1285Repository extends CrudRepository<Customer1285, Long> {

	List<Customer1285> findByLastName(String lastName);
}
