package example.repo;

import example.model.Customer11;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer11Repository extends CrudRepository<Customer11, Long> {

	List<Customer11> findByLastName(String lastName);
}
