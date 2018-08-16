package example.repo;

import example.model.Customer664;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer664Repository extends CrudRepository<Customer664, Long> {

	List<Customer664> findByLastName(String lastName);
}
