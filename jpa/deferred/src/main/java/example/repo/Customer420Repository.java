package example.repo;

import example.model.Customer420;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer420Repository extends CrudRepository<Customer420, Long> {

	List<Customer420> findByLastName(String lastName);
}
