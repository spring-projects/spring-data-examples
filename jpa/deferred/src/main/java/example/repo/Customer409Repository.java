package example.repo;

import example.model.Customer409;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer409Repository extends CrudRepository<Customer409, Long> {

	List<Customer409> findByLastName(String lastName);
}
