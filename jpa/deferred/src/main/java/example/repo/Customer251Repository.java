package example.repo;

import example.model.Customer251;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer251Repository extends CrudRepository<Customer251, Long> {

	List<Customer251> findByLastName(String lastName);
}
