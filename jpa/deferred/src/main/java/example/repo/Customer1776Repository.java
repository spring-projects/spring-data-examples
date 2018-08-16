package example.repo;

import example.model.Customer1776;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1776Repository extends CrudRepository<Customer1776, Long> {

	List<Customer1776> findByLastName(String lastName);
}
