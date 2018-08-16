package example.repo;

import example.model.Customer691;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer691Repository extends CrudRepository<Customer691, Long> {

	List<Customer691> findByLastName(String lastName);
}
