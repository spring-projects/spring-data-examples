package example.repo;

import example.model.Customer982;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer982Repository extends CrudRepository<Customer982, Long> {

	List<Customer982> findByLastName(String lastName);
}
