package com.photoChallenger.tripture.domain.point.repository;

import com.photoChallenger.tripture.domain.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
}
