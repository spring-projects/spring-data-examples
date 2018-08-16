package example.repo;

import example.model.Customer901;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer901Repository extends CrudRepository<Customer901, Long> {

	List<Customer901> findByLastName(String lastName);
}
