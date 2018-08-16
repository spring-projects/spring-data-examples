package example.repo;

import example.model.Customer168;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer168Repository extends CrudRepository<Customer168, Long> {

	List<Customer168> findByLastName(String lastName);
}
