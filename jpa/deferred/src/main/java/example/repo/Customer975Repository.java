package example.repo;

import example.model.Customer975;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer975Repository extends CrudRepository<Customer975, Long> {

	List<Customer975> findByLastName(String lastName);
}
