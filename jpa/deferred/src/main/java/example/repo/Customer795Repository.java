package example.repo;

import example.model.Customer795;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer795Repository extends CrudRepository<Customer795, Long> {

	List<Customer795> findByLastName(String lastName);
}
