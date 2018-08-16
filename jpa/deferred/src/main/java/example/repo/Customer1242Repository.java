package example.repo;

import example.model.Customer1242;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1242Repository extends CrudRepository<Customer1242, Long> {

	List<Customer1242> findByLastName(String lastName);
}
