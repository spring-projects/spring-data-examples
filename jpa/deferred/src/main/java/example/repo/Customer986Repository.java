package example.repo;

import example.model.Customer986;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer986Repository extends CrudRepository<Customer986, Long> {

	List<Customer986> findByLastName(String lastName);
}
