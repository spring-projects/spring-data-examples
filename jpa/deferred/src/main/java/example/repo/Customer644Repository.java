package example.repo;

import example.model.Customer644;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer644Repository extends CrudRepository<Customer644, Long> {

	List<Customer644> findByLastName(String lastName);
}
