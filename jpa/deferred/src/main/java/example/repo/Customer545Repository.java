package example.repo;

import example.model.Customer545;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer545Repository extends CrudRepository<Customer545, Long> {

	List<Customer545> findByLastName(String lastName);
}
