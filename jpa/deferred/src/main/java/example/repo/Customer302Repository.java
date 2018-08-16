package example.repo;

import example.model.Customer302;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer302Repository extends CrudRepository<Customer302, Long> {

	List<Customer302> findByLastName(String lastName);
}
