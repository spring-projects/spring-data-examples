package example.repo;

import example.model.Customer523;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer523Repository extends CrudRepository<Customer523, Long> {

	List<Customer523> findByLastName(String lastName);
}
