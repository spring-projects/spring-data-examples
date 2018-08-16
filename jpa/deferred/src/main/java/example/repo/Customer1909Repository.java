package example.repo;

import example.model.Customer1909;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1909Repository extends CrudRepository<Customer1909, Long> {

	List<Customer1909> findByLastName(String lastName);
}
