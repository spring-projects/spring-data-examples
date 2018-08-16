package example.repo;

import example.model.Customer236;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer236Repository extends CrudRepository<Customer236, Long> {

	List<Customer236> findByLastName(String lastName);
}
