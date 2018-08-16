package example.repo;

import example.model.Customer1027;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1027Repository extends CrudRepository<Customer1027, Long> {

	List<Customer1027> findByLastName(String lastName);
}
