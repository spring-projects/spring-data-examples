package example.repo;

import example.model.Customer909;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer909Repository extends CrudRepository<Customer909, Long> {

	List<Customer909> findByLastName(String lastName);
}
