package example.repo;

import example.model.Customer970;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer970Repository extends CrudRepository<Customer970, Long> {

	List<Customer970> findByLastName(String lastName);
}
