package example.repo;

import example.model.Customer1725;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1725Repository extends CrudRepository<Customer1725, Long> {

	List<Customer1725> findByLastName(String lastName);
}
