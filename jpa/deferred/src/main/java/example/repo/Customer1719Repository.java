package example.repo;

import example.model.Customer1719;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1719Repository extends CrudRepository<Customer1719, Long> {

	List<Customer1719> findByLastName(String lastName);
}
