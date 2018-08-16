package example.repo;

import example.model.Customer584;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer584Repository extends CrudRepository<Customer584, Long> {

	List<Customer584> findByLastName(String lastName);
}
