package example.repo;

import example.model.Customer794;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer794Repository extends CrudRepository<Customer794, Long> {

	List<Customer794> findByLastName(String lastName);
}
