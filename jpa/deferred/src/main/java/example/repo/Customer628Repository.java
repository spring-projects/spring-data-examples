package example.repo;

import example.model.Customer628;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer628Repository extends CrudRepository<Customer628, Long> {

	List<Customer628> findByLastName(String lastName);
}
