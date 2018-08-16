package example.repo;

import example.model.Customer161;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer161Repository extends CrudRepository<Customer161, Long> {

	List<Customer161> findByLastName(String lastName);
}
