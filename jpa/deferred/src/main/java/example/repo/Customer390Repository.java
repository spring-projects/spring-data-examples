package example.repo;

import example.model.Customer390;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer390Repository extends CrudRepository<Customer390, Long> {

	List<Customer390> findByLastName(String lastName);
}
