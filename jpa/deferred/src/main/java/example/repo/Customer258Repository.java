package example.repo;

import example.model.Customer258;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer258Repository extends CrudRepository<Customer258, Long> {

	List<Customer258> findByLastName(String lastName);
}
