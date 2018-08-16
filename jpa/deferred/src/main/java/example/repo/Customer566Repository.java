package example.repo;

import example.model.Customer566;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer566Repository extends CrudRepository<Customer566, Long> {

	List<Customer566> findByLastName(String lastName);
}
