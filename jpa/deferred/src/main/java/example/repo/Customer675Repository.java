package example.repo;

import example.model.Customer675;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer675Repository extends CrudRepository<Customer675, Long> {

	List<Customer675> findByLastName(String lastName);
}
