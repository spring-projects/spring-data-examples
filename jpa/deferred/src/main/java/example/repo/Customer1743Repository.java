package example.repo;

import example.model.Customer1743;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1743Repository extends CrudRepository<Customer1743, Long> {

	List<Customer1743> findByLastName(String lastName);
}
