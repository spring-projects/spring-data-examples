package example.repo;

import example.model.Customer419;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer419Repository extends CrudRepository<Customer419, Long> {

	List<Customer419> findByLastName(String lastName);
}
