package org.choongang.member.repositories;

import org.choongang.member.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AddressRepositoriy extends JpaRepository<Address, Long>, QuerydslPredicateExecutor<Address> {

}
