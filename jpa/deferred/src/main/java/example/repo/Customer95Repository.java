package example.repo;

import example.model.Customer95;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer95Repository extends CrudRepository<Customer95, Long> {

	List<Customer95> findByLastName(String lastName);
}
