package example.repo;

import example.model.Customer960;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer960Repository extends CrudRepository<Customer960, Long> {

	List<Customer960> findByLastName(String lastName);
}
