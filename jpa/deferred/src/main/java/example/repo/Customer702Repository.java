package example.repo;

import example.model.Customer702;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer702Repository extends CrudRepository<Customer702, Long> {

	List<Customer702> findByLastName(String lastName);
}
