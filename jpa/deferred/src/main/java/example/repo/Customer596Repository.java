package example.repo;

import example.model.Customer596;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer596Repository extends CrudRepository<Customer596, Long> {

	List<Customer596> findByLastName(String lastName);
}
