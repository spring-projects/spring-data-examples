package example.repo;

import example.model.Customer6;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer6Repository extends CrudRepository<Customer6, Long> {

	List<Customer6> findByLastName(String lastName);
}
