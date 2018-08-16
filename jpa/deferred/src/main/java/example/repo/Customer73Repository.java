package example.repo;

import example.model.Customer73;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer73Repository extends CrudRepository<Customer73, Long> {

	List<Customer73> findByLastName(String lastName);
}
