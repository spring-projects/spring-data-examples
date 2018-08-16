package example.repo;

import example.model.Customer94;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer94Repository extends CrudRepository<Customer94, Long> {

	List<Customer94> findByLastName(String lastName);
}
