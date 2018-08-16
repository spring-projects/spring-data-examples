package example.repo;

import example.model.Customer1984;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1984Repository extends CrudRepository<Customer1984, Long> {

	List<Customer1984> findByLastName(String lastName);
}
