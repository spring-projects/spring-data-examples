package example.repo;

import example.model.Customer1986;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1986Repository extends CrudRepository<Customer1986, Long> {

	List<Customer1986> findByLastName(String lastName);
}
