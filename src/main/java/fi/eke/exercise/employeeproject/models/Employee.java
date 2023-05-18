package fi.eke.exercise.employeeproject.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "emp_id")
    private Integer EmployeeId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "family_name")
    private String FamilyName;

    @Column(name = "email")
    private String Email;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "deptId", nullable = false)
    private Department department;

    @ManyToMany
    private List<Project> projects;

}
