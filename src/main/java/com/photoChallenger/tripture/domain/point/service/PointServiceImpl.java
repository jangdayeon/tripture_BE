package com.photoChallenger.tripture.domain.point.service;

import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.point.dto.PointDto;
import com.photoChallenger.tripture.domain.point.dto.PointListResponse;
import com.photoChallenger.tripture.domain.point.entity.Point;
import com.photoChallenger.tripture.domain.point.repository.PointRepository;
import com.photoChallenger.tripture.domain.post.repository.PostRepository;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import com.photoChallenger.tripture.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointServiceImpl implements PointService{
    private final LoginRepository loginRepository;
    private final PostRepository postRepository;
    private final ReportRepository reportRepository;
    private final PointRepository pointRepository;

    @Override
    public PointListResponse getPointList(int pageNo, LocalDate startDate, LocalDate endDate, Long loginId) {
        Profile profile = loginRepository.getById(loginId).getProfile();
        Pageable pageable = PageRequest.of(pageNo,5, Sort.by(Sort.Direction.DESC, "pointDate"));
        Page<Point> page = pointRepository.findPointByProfile_ProfileIdWhereBetweenStartDateAndEndDate(profile.getProfileId(), startDate, endDate, pageable);
        List<PointDto> pointDtos = page.getContent().stream().map((point)->{
            return PointDto.from(point);
        }).toList();
        return new PointListResponse(page.getTotalPages(),pointDtos);
    }
}
