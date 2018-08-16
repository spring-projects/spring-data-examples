package example.repo;

import example.model.Customer347;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer347Repository extends CrudRepository<Customer347, Long> {

	List<Customer347> findByLastName(String lastName);
}
