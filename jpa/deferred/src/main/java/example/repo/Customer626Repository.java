package example.repo;

import example.model.Customer626;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer626Repository extends CrudRepository<Customer626, Long> {

	List<Customer626> findByLastName(String lastName);
}
