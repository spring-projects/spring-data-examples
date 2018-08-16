package example.repo;

import example.model.Customer976;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer976Repository extends CrudRepository<Customer976, Long> {

	List<Customer976> findByLastName(String lastName);
}
