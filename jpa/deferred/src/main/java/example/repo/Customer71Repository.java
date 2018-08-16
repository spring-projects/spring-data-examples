package example.repo;

import example.model.Customer71;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer71Repository extends CrudRepository<Customer71, Long> {

	List<Customer71> findByLastName(String lastName);
}
