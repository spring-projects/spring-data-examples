package example.repo;

import example.model.Customer524;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer524Repository extends CrudRepository<Customer524, Long> {

	List<Customer524> findByLastName(String lastName);
}
