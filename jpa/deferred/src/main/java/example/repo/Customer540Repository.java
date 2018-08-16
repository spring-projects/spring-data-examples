package example.repo;

import example.model.Customer540;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer540Repository extends CrudRepository<Customer540, Long> {

	List<Customer540> findByLastName(String lastName);
}
