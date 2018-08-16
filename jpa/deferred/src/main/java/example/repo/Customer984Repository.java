package example.repo;

import example.model.Customer984;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer984Repository extends CrudRepository<Customer984, Long> {

	List<Customer984> findByLastName(String lastName);
}
