package example.repo;

import example.model.Customer1493;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1493Repository extends CrudRepository<Customer1493, Long> {

	List<Customer1493> findByLastName(String lastName);
}
