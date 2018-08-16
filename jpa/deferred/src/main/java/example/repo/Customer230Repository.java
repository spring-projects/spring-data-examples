package example.repo;

import example.model.Customer230;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer230Repository extends CrudRepository<Customer230, Long> {

	List<Customer230> findByLastName(String lastName);
}
