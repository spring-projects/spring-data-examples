package example.repo;

import example.model.Customer843;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer843Repository extends CrudRepository<Customer843, Long> {

	List<Customer843> findByLastName(String lastName);
}
