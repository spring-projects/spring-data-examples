package example.repo;

import example.model.Customer1269;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1269Repository extends CrudRepository<Customer1269, Long> {

	List<Customer1269> findByLastName(String lastName);
}
