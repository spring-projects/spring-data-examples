package example.repo;

import example.model.Customer1693;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1693Repository extends CrudRepository<Customer1693, Long> {

	List<Customer1693> findByLastName(String lastName);
}
