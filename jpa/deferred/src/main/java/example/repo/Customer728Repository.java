package example.repo;

import example.model.Customer728;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer728Repository extends CrudRepository<Customer728, Long> {

	List<Customer728> findByLastName(String lastName);
}
