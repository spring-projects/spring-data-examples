package example.repo;

import example.model.Customer439;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer439Repository extends CrudRepository<Customer439, Long> {

	List<Customer439> findByLastName(String lastName);
}
