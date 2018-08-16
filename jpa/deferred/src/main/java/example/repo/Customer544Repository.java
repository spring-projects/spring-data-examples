package example.repo;

import example.model.Customer544;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer544Repository extends CrudRepository<Customer544, Long> {

	List<Customer544> findByLastName(String lastName);
}
