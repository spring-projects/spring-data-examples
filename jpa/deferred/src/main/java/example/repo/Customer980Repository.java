package example.repo;

import example.model.Customer980;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer980Repository extends CrudRepository<Customer980, Long> {

	List<Customer980> findByLastName(String lastName);
}
