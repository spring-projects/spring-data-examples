package example.repo;

import example.model.Customer217;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer217Repository extends CrudRepository<Customer217, Long> {

	List<Customer217> findByLastName(String lastName);
}
