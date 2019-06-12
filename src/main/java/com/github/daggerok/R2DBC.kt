package com.github.daggerok

import org.springframework.data.annotation.Id
import org.springframework.data.r2dbc.repository.query.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

data class Employee(val name: String,
                    val salary: Int,
                    val organizationId: Long,
                    @Id var id: Int? = null)

interface EmployeeRepository : ReactiveCrudRepository<Employee, Long> {
  @Query("select id, name, salary, organization_id from employee e where e.organization_id = $1")
  fun findByOrganizationId(organizationId: Int) : Flux<Employee>
}
