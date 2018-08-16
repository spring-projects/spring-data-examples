package example.repo;

import example.model.Customer674;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer674Repository extends CrudRepository<Customer674, Long> {

	List<Customer674> findByLastName(String lastName);
}
