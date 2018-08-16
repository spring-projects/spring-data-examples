package example.repo;

import example.model.Customer1064;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1064Repository extends CrudRepository<Customer1064, Long> {

	List<Customer1064> findByLastName(String lastName);
}
