package example.repo;

import example.model.Customer934;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer934Repository extends CrudRepository<Customer934, Long> {

	List<Customer934> findByLastName(String lastName);
}
