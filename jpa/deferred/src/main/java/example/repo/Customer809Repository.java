package example.repo;

import example.model.Customer809;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer809Repository extends CrudRepository<Customer809, Long> {

	List<Customer809> findByLastName(String lastName);
}
