package example.repo;

import example.model.Customer624;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer624Repository extends CrudRepository<Customer624, Long> {

	List<Customer624> findByLastName(String lastName);
}
