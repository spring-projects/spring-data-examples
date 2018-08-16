package example.repo;

import example.model.Customer463;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer463Repository extends CrudRepository<Customer463, Long> {

	List<Customer463> findByLastName(String lastName);
}
