package example.repo;

import example.model.Customer995;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer995Repository extends CrudRepository<Customer995, Long> {

	List<Customer995> findByLastName(String lastName);
}
