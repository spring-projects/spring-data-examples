package example.repo;

import example.model.Customer119;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer119Repository extends CrudRepository<Customer119, Long> {

	List<Customer119> findByLastName(String lastName);
}
