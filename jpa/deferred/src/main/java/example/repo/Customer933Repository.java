package example.repo;

import example.model.Customer933;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer933Repository extends CrudRepository<Customer933, Long> {

	List<Customer933> findByLastName(String lastName);
}
