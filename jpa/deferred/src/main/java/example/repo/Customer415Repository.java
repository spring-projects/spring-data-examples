package example.repo;

import example.model.Customer415;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer415Repository extends CrudRepository<Customer415, Long> {

	List<Customer415> findByLastName(String lastName);
}
