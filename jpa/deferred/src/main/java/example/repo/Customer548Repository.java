package example.repo;

import example.model.Customer548;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer548Repository extends CrudRepository<Customer548, Long> {

	List<Customer548> findByLastName(String lastName);
}
