package example.repo;

import example.model.Customer1119;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1119Repository extends CrudRepository<Customer1119, Long> {

	List<Customer1119> findByLastName(String lastName);
}
