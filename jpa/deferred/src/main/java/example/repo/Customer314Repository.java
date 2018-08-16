package example.repo;

import example.model.Customer314;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer314Repository extends CrudRepository<Customer314, Long> {

	List<Customer314> findByLastName(String lastName);
}
