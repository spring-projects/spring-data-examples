package example.repo;

import example.model.Customer686;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer686Repository extends CrudRepository<Customer686, Long> {

	List<Customer686> findByLastName(String lastName);
}
