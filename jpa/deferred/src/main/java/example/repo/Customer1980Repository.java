package example.repo;

import example.model.Customer1980;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1980Repository extends CrudRepository<Customer1980, Long> {

	List<Customer1980> findByLastName(String lastName);
}
