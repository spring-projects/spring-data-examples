package example.repo;

import example.model.Customer721;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer721Repository extends CrudRepository<Customer721, Long> {

	List<Customer721> findByLastName(String lastName);
}
