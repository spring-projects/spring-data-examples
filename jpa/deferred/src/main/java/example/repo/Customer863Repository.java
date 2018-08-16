package example.repo;

import example.model.Customer863;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer863Repository extends CrudRepository<Customer863, Long> {

	List<Customer863> findByLastName(String lastName);
}
