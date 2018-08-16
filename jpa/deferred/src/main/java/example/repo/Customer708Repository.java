package example.repo;

import example.model.Customer708;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer708Repository extends CrudRepository<Customer708, Long> {

	List<Customer708> findByLastName(String lastName);
}
