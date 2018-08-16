package example.repo;

import example.model.Customer75;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer75Repository extends CrudRepository<Customer75, Long> {

	List<Customer75> findByLastName(String lastName);
}
