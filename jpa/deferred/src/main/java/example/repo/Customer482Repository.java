package example.repo;

import example.model.Customer482;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer482Repository extends CrudRepository<Customer482, Long> {

	List<Customer482> findByLastName(String lastName);
}
