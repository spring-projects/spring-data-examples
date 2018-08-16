package example.repo;

import example.model.Customer907;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer907Repository extends CrudRepository<Customer907, Long> {

	List<Customer907> findByLastName(String lastName);
}
