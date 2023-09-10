package com.popcornblog.movies.adapters.out.repositories;


import com.popcornblog.movies.adapters.out.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    List<MovieEntity> findByLaunchDateBetween(LocalDate initialDate, LocalDate endDate);

}
