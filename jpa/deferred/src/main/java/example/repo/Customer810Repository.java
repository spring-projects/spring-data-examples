package example.repo;

import example.model.Customer810;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer810Repository extends CrudRepository<Customer810, Long> {

	List<Customer810> findByLastName(String lastName);
}
