package example.repo;

import example.model.Customer285;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer285Repository extends CrudRepository<Customer285, Long> {

	List<Customer285> findByLastName(String lastName);
}
