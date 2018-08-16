package example.repo;

import example.model.Customer734;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer734Repository extends CrudRepository<Customer734, Long> {

	List<Customer734> findByLastName(String lastName);
}
