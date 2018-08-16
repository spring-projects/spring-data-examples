package example.repo;

import example.model.Customer997;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer997Repository extends CrudRepository<Customer997, Long> {

	List<Customer997> findByLastName(String lastName);
}
