package example.repo;

import example.model.Customer703;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer703Repository extends CrudRepository<Customer703, Long> {

	List<Customer703> findByLastName(String lastName);
}
