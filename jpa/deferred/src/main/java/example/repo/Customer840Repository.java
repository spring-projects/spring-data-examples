package example.repo;

import example.model.Customer840;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer840Repository extends CrudRepository<Customer840, Long> {

	List<Customer840> findByLastName(String lastName);
}
