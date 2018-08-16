package example.repo;

import example.model.Customer1258;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1258Repository extends CrudRepository<Customer1258, Long> {

	List<Customer1258> findByLastName(String lastName);
}
