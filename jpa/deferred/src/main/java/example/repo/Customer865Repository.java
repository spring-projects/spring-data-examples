package example.repo;

import example.model.Customer865;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer865Repository extends CrudRepository<Customer865, Long> {

	List<Customer865> findByLastName(String lastName);
}
