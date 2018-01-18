/*
1)	평균 연봉(salary)이 가장 높은 나라는?
 */
select country_name,
        b.max_avg
from locations loc, countries coun
        ,(select dep.location_id, avg(salary) avg
          from employees emp, departments dep
          where emp.department_id=dep.department_id
          group by dep.location_id) a, (select max(avg(salary)) max_avg
                                        from employees emp, departments dep
                                        where emp.department_id=dep.department_id
                                        group by dep.location_id) b
where loc.country_id=coun.country_id
and loc.location_id=a.location_id
and a.avg = b.max_avg;

/*
2)	평균 연봉(salary)이 가장 높은 지역은?
*/
select region_name,
        b.max_avg
from locations loc, countries coun, regions reg
        ,(select dep.location_id, avg(salary) avg
          from employees emp, departments dep
          where emp.department_id=dep.department_id
          group by dep.location_id) a, (select max(avg(salary)) max_avg
                                        from employees emp, departments dep
                                        where emp.department_id=dep.department_id
                                        group by dep.location_id) b
where loc.country_id=coun.country_id
and coun.region_id=reg.region_id
and loc.location_id=a.location_id
and a.avg = b.max_avg;

/*
3)	가장 많은 직원이 있는 부서는 어떤 부서인가요?
*/
select department_name,
        b.max_cou
from departments dep, (select department_id, count(employee_id) cou
                        from employees
                        group by department_id) a, (select max(count(employee_id)) max_cou
                                                        from employees
                                                        group by department_id) b
where dep.department_id=a.department_id
and a.cou=b.max_cou;
