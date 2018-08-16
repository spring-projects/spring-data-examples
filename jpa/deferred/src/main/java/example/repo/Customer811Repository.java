package example.repo;

import example.model.Customer811;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer811Repository extends CrudRepository<Customer811, Long> {

	List<Customer811> findByLastName(String lastName);
}
