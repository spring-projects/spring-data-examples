package example.repo;

import example.model.Customer292;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer292Repository extends CrudRepository<Customer292, Long> {

	List<Customer292> findByLastName(String lastName);
}
