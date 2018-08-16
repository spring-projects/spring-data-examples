package example.repo;

import example.model.Customer814;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer814Repository extends CrudRepository<Customer814, Long> {

	List<Customer814> findByLastName(String lastName);
}
