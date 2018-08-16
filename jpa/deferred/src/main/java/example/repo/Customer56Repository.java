package example.repo;

import example.model.Customer56;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer56Repository extends CrudRepository<Customer56, Long> {

	List<Customer56> findByLastName(String lastName);
}
