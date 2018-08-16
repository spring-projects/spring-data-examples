package example.repo;

import example.model.Customer1674;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1674Repository extends CrudRepository<Customer1674, Long> {

	List<Customer1674> findByLastName(String lastName);
}
