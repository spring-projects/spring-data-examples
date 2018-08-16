package example.repo;

import example.model.Customer412;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer412Repository extends CrudRepository<Customer412, Long> {

	List<Customer412> findByLastName(String lastName);
}
