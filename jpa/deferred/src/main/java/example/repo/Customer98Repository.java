package example.repo;

import example.model.Customer98;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer98Repository extends CrudRepository<Customer98, Long> {

	List<Customer98> findByLastName(String lastName);
}
