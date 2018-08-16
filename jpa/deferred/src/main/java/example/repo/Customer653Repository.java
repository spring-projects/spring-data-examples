package example.repo;

import example.model.Customer653;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer653Repository extends CrudRepository<Customer653, Long> {

	List<Customer653> findByLastName(String lastName);
}
