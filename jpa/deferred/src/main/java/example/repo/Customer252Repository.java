package example.repo;

import example.model.Customer252;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer252Repository extends CrudRepository<Customer252, Long> {

	List<Customer252> findByLastName(String lastName);
}
