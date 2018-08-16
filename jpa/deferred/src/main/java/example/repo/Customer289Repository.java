package example.repo;

import example.model.Customer289;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer289Repository extends CrudRepository<Customer289, Long> {

	List<Customer289> findByLastName(String lastName);
}
