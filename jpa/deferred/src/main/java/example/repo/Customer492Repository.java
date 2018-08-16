package example.repo;

import example.model.Customer492;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer492Repository extends CrudRepository<Customer492, Long> {

	List<Customer492> findByLastName(String lastName);
}
