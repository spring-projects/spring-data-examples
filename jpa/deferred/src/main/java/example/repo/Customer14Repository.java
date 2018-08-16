package example.repo;

import example.model.Customer14;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer14Repository extends CrudRepository<Customer14, Long> {

	List<Customer14> findByLastName(String lastName);
}
