package example.repo;

import example.model.Customer1664;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1664Repository extends CrudRepository<Customer1664, Long> {

	List<Customer1664> findByLastName(String lastName);
}
