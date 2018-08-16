package example.repo;

import example.model.Customer923;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer923Repository extends CrudRepository<Customer923, Long> {

	List<Customer923> findByLastName(String lastName);
}
