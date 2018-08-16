package example.repo;

import example.model.Customer992;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer992Repository extends CrudRepository<Customer992, Long> {

	List<Customer992> findByLastName(String lastName);
}
