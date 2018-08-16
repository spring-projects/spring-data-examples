package example.repo;

import example.model.Customer136;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer136Repository extends CrudRepository<Customer136, Long> {

	List<Customer136> findByLastName(String lastName);
}
