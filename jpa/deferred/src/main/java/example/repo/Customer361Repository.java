package example.repo;

import example.model.Customer361;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer361Repository extends CrudRepository<Customer361, Long> {

	List<Customer361> findByLastName(String lastName);
}
