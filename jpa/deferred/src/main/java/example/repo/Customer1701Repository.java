package example.repo;

import example.model.Customer1701;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1701Repository extends CrudRepository<Customer1701, Long> {

	List<Customer1701> findByLastName(String lastName);
}
