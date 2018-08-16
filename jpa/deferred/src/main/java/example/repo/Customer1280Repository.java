package example.repo;

import example.model.Customer1280;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1280Repository extends CrudRepository<Customer1280, Long> {

	List<Customer1280> findByLastName(String lastName);
}
