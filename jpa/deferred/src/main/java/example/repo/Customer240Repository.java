package example.repo;

import example.model.Customer240;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer240Repository extends CrudRepository<Customer240, Long> {

	List<Customer240> findByLastName(String lastName);
}
