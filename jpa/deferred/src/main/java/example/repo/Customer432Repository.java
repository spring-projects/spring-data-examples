package example.repo;

import example.model.Customer432;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer432Repository extends CrudRepository<Customer432, Long> {

	List<Customer432> findByLastName(String lastName);
}
