package example.repo;

import example.model.Customer121;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer121Repository extends CrudRepository<Customer121, Long> {

	List<Customer121> findByLastName(String lastName);
}
