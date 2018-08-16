package example.repo;

import example.model.Customer565;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer565Repository extends CrudRepository<Customer565, Long> {

	List<Customer565> findByLastName(String lastName);
}
