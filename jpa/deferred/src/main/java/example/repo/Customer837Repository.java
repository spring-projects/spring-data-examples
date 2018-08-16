package example.repo;

import example.model.Customer837;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer837Repository extends CrudRepository<Customer837, Long> {

	List<Customer837> findByLastName(String lastName);
}
