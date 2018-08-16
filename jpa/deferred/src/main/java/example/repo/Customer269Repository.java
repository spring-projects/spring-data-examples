package example.repo;

import example.model.Customer269;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer269Repository extends CrudRepository<Customer269, Long> {

	List<Customer269> findByLastName(String lastName);
}
