package example.repo;

import example.model.Customer725;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer725Repository extends CrudRepository<Customer725, Long> {

	List<Customer725> findByLastName(String lastName);
}
