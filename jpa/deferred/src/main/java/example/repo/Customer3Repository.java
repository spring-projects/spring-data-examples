package example.repo;

import example.model.Customer3;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer3Repository extends CrudRepository<Customer3, Long> {

	List<Customer3> findByLastName(String lastName);
}
