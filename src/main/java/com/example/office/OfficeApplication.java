package com.example.office;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Arrays;

import com.example.office.model.Department;
import com.example.office.model.Employee;
import com.example.office.model.Project;
import com.example.office.repository.DepartmentRepository;
import com.example.office.repository.EmployeeRepository;
import com.example.office.repository.ProjectRepository;

@SpringBootApplication
public class OfficeApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(
		OfficeApplication.class
	);

	private final EmployeeRepository employeeRepository;
	private final ProjectRepository projectRepository;
	private final DepartmentRepository departmentRepository;

	

	public OfficeApplication(EmployeeRepository employeeRepository, ProjectRepository projectRepository,
			DepartmentRepository departmentRepository) {
		this.employeeRepository = employeeRepository;
		this.projectRepository = projectRepository;
		this.departmentRepository = departmentRepository;
	}



	public static void main(String[] args) {
		SpringApplication.run(OfficeApplication.class, args);
	}



	@Override
	public void run(String... args) throws Exception {
		Project p1 = new Project("Project A");
		Project p2 = new Project("Project B");
		projectRepository.saveAll(Arrays.asList(p1, p2));

		Department d1 = new Department("Accounting");
		Department d2 = new Department("Human Resource");
		departmentRepository.saveAll(Arrays.asList(d1, d2));

		employeeRepository.save(new Employee("Phakpoom", 30000, d1, p1));
		employeeRepository.save(new Employee("Ittipon", 30000, d2, p1));
		employeeRepository.save(new Employee("Supanan", 30000, d1, p2));
		employeeRepository.save(new Employee("Sitthichai", 30000, d2, p2));

		for(Employee e: employeeRepository.findAll()){
			Department d = e.getDepartment();
			Project p = e.getProject();
			logger.info("Name: {}, Department:{}, Project:{}", e.getName(), d.getName(), p.getName());
		}

		for(Department d: departmentRepository.findAll()){
			logger.info("Name: {}", d.getName());
			logger.info("----- Employees -----");
			for(Employee e: d.getEmployees()){
				logger.info("Name: {}, Salary: {}", e.getName(), e.getSalary());
			}
		}
	}

}
