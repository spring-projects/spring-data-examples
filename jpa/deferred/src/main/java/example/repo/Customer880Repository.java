package example.repo;

import example.model.Customer880;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer880Repository extends CrudRepository<Customer880, Long> {

	List<Customer880> findByLastName(String lastName);
}
