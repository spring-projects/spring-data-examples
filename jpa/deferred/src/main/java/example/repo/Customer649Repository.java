package example.repo;

import example.model.Customer649;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer649Repository extends CrudRepository<Customer649, Long> {

	List<Customer649> findByLastName(String lastName);
}
