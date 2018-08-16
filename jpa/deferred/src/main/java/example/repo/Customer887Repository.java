package example.repo;

import example.model.Customer887;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer887Repository extends CrudRepository<Customer887, Long> {

	List<Customer887> findByLastName(String lastName);
}
