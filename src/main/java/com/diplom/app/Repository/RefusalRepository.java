package com.diplom.app.Repository;

import com.diplom.app.Entity.Refusal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefusalRepository extends JpaRepository<Refusal, Long> {
}
