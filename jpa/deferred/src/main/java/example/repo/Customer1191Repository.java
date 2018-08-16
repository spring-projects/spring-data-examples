package example.repo;

import example.model.Customer1191;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1191Repository extends CrudRepository<Customer1191, Long> {

	List<Customer1191> findByLastName(String lastName);
}
