package com.yoru.qingxintutor.service;

import com.yoru.qingxintutor.exception.BusinessException;
import com.yoru.qingxintutor.mapper.*;
import com.yoru.qingxintutor.pojo.dto.request.ReservationCreateRequest;
import com.yoru.qingxintutor.pojo.entity.ReservationEntity;
import com.yoru.qingxintutor.pojo.entity.UserEntity;
import com.yoru.qingxintutor.pojo.entity.UserOrderEntity;
import com.yoru.qingxintutor.pojo.result.ReservationInfoResult;
import com.yoru.qingxintutor.utils.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ReservationService {

    @Autowired
    private UserOrderMapper orderMapper;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private WalletService walletService;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EmailUtils emailUtils;

    public List<ReservationInfoResult> listStudentReservations(String userId, ReservationEntity.State state) {
        String username = userMapper.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found"))
                .getUsername();
        return reservationMapper.findByUserIdAndState(userId, state)
                .stream()
                .map(reservation -> entityToResult(reservation,
                        username,
                        teacherMapper.findNameById(reservation.getTeacherId())
                                .orElseThrow(() -> new BusinessException("Teacher not found")),
                        subjectMapper.findById(reservation.getSubjectId())
                                .orElseThrow(() -> new BusinessException("Subject not found"))
                                .getSubjectName())
                )
                .toList();
    }

    public List<ReservationInfoResult> listTeacherReservations(String teacherUserId, ReservationEntity.State state) {
        Long teacherId = teacherMapper.findTeacherIdByUserId(teacherUserId)
                .orElseThrow(() -> new BusinessException("Teacher not found"));
        String teacherName = teacherMapper.findNameById(teacherId)
                .orElseThrow(() -> new BusinessException("Teacher not found"));
        return reservationMapper.findByTeacherIdAndState(teacherId, state)
                .stream()
                .map(reservation -> entityToResult(reservation,
                        userMapper.findById(reservation.getUserId())
                                .orElseThrow(() -> new BusinessException("User not found"))
                                .getUsername(),
                        teacherName,
                        subjectMapper.findById(reservation.getSubjectId())
                                .orElseThrow(() -> new BusinessException("Subject not found"))
                                .getSubjectName())
                )
                .toList();
    }

    public ReservationInfoResult getStudentReservation(String userId, Long id) {
        String username = userMapper.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found"))
                .getUsername();
        ReservationEntity reservation = reservationMapper.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BusinessException("Reservation not found"));
        return entityToResult(reservation, username,
                teacherMapper.findNameById(reservation.getTeacherId())
                        .orElseThrow(() -> new BusinessException("Teacher not found")),
                subjectMapper.findById(reservation.getSubjectId())
                        .orElseThrow(() -> new BusinessException("Subject not found"))
                        .getSubjectName());
    }

    public ReservationInfoResult getTeacherReservation(String teacherUserId, Long id) {
        Long teacherId = teacherMapper.findTeacherIdByUserId(teacherUserId)
                .orElseThrow(() -> new BusinessException("Teacher not found"));
        String teacherName = teacherMapper.findNameById(teacherId)
                .orElseThrow(() -> new BusinessException("Teacher not found"));
        ReservationEntity reservation = reservationMapper.findByIdAndTeacherId(id, teacherId)
                .orElseThrow(() -> new BusinessException("Reservation not found"));
        return entityToResult(reservation,
                userMapper.findById(reservation.getUserId())
                        .orElseThrow(() -> new BusinessException("User not found"))
                        .getUsername(),
                teacherName,
                subjectMapper.findById(reservation.getSubjectId())
                        .orElseThrow(() -> new BusinessException("Subject not found"))
                        .getSubjectName());
    }

    public ReservationInfoResult createReservation(String userId, ReservationCreateRequest request) {
        String username = userMapper.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found"))
                .getUsername();
        String teacherName = teacherMapper.findNameById(request.getTeacherId())
                .orElseThrow(() -> new BusinessException("Teacher not found"));
        Long subjectId = subjectMapper.findBySubjectName(request.getSubjectName().trim())
                .orElseThrow(() -> new BusinessException("Subject not found"))
                .getId();
        if (reservationMapper.existsConflict(request.getTeacherId(),
                request.getStartTime(),
                request.getStartTime().plusMinutes(request.getDuration())))
            throw new BusinessException("Reservation time conflicts with the teacher's time, " +
                    "please select a different time period");
        ReservationEntity reservation = ReservationEntity.builder()
                .userId(userId)
                .teacherId(request.getTeacherId())
                .subjectId(subjectId)
                .startTime(request.getStartTime())
                .duration(request.getDuration())
                .state(ReservationEntity.State.PENDING)
                .createTime(LocalDateTime.now())
                .build();
        reservationMapper.insert(reservation);
        return entityToResult(reservation, username, teacherName, request.getSubjectName().trim());
    }

    @Transactional
    public void cancelReservationByStudent(String userId, Long id) {
        ReservationEntity reservation = reservationMapper.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BusinessException("Reservation not found"));

        cancelReservation(reservation);
    }

    @Transactional
    public void cancelReservationByTeacher(String teacherUserId, Long id) {
        Long teacherId = teacherMapper.findTeacherIdByUserId(teacherUserId)
                .orElseThrow(() -> new BusinessException("Teacher not found"));
        ReservationEntity reservation = reservationMapper.findByIdAndTeacherId(id, teacherId)
                .orElseThrow(() -> new BusinessException("Reservation not found"));

        cancelReservation(reservation);
    }

    @Transactional
    public void confirmReservation(String teacherUserId, Long id) {
        Long teacherId = teacherMapper.findTeacherIdByUserId(teacherUserId)
                .orElseThrow(() -> new BusinessException("Teacher not found"));
        ReservationEntity reservation = reservationMapper.findByIdAndTeacherId(id, teacherId)
                .orElseThrow(() -> new BusinessException("Reservation not found"));
        if (reservation.getState() != ReservationEntity.State.PENDING)
            throw new BusinessException("Only pending reservation can be confirmed");
        if (!orderMapper.hasNoPendingOrders(id))
            throw new BusinessException("One or more orders are not paid by student yet.");
        reservationMapper.updateState(id, ReservationEntity.State.CONFIRMED);
    }

    @Transactional
    public void completeReservation(String teacherUserId, Long id) {
        Long teacherId = teacherMapper.findTeacherIdByUserId(teacherUserId)
                .orElseThrow(() -> new BusinessException("Teacher not found"));
        ReservationEntity reservation = reservationMapper.findByIdAndTeacherId(id, teacherId)
                .orElseThrow(() -> new BusinessException("Reservation not found"));
        if (reservation.getState() != ReservationEntity.State.CONFIRMED)
            throw new BusinessException("Only confirmed reservation can be completed");
        reservationMapper.updateState(id, ReservationEntity.State.COMPLETED);
        voucherService.issue(reservation.getUserId(), BigDecimal.TEN);
    }


    // 定时任务 - 课程开始前发送上课提醒
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void sendClassReminders() {
        reservationMapper.findReservationsStartingInNext10Minutes().forEach(
                reservation -> {
                    try {
                        UserEntity student = userMapper.findById(reservation.getUserId())
                                .orElseThrow(() -> new BusinessException("User not found"));
                        String teacherName = teacherMapper.findNameById(reservation.getTeacherId())
                                .orElseThrow(() -> new BusinessException("Teacher not found"));
                        notificationService.createPersonalNotification(reservation.getUserId(),
                                "Lesson start reminder",
                                "Your lesson will begin in ten minutes! Remember to attend lesson.");
                        emailUtils.sendLessonReminder(student.getEmail(),
                                student.getUsername(),
                                teacherName,
                                reservation.getStartTime(),
                                reservation.getDuration()
                        );
                        log.debug("Sent class reminder for reservation ID: {}", reservation.getId());
                    } catch (Exception e) {
                        log.error("Failed to send reminder for reservation ID: {}, {}", reservation.getId()
                                , e.getMessage());
                    }
                }
        );
    }

    // 定时任务 - 课程结束后 12 小时自动标记为 COMPLETED
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    @Transactional
    public void autoConfirmCompletedReservation() {
        reservationMapper.getReservationsReadyForCompletion()
                .forEach(reservation -> {
                    reservationMapper.updateState(reservation.getId(), ReservationEntity.State.COMPLETED);
                    voucherService.issue(reservation.getUserId(), BigDecimal.TEN);
                });
    }

    // 定时任务 - 超过课程开始时间自动标记为 CANCELED
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    @Transactional
    public void autoCancelExpiredReservation() {
        reservationMapper.getExpiredPendingReservations()
                .forEach(reservation -> {
                    try {
                        cancelReservation(reservation);
                    } catch (Exception e) {
                        log.warn("Failed to auto-cancel reservation ID {} : {}",
                                reservation.getId(), e.getMessage());
                    }
                });
    }


    @Transactional
    public void cancelReservation(ReservationEntity reservation) {
        ReservationEntity.State state = reservation.getState();
        if (state == ReservationEntity.State.PENDING) {
            Long reservationId = reservation.getId();
            orderMapper.findByReservationId(reservationId)
                    .forEach(order -> {
                        UserOrderEntity.State orderState = order.getState();
                        if (orderState == UserOrderEntity.State.PENDING) {
                            orderMapper.updateState(order.getId(), UserOrderEntity.State.CANCELED);
                        } else if (orderState == UserOrderEntity.State.PAID) {
                            orderMapper.updateState(order.getId(), UserOrderEntity.State.CANCELED);
                            walletService.addBalance(order.getUserId(),
                                    order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())));
                        } else if (orderState != UserOrderEntity.State.CANCELED) {
                            log.warn("Unexpected order state {} for order ID {}, skipping cancellation",
                                    orderState, order.getId());
                        }
                    });
            reservationMapper.updateState(reservationId, ReservationEntity.State.CANCELED);
        } else if (state == ReservationEntity.State.CONFIRMED || state == ReservationEntity.State.COMPLETED) {
            throw new BusinessException("Only pending reservation can be canceled");
        } else if (state == ReservationEntity.State.CANCELED) {
            throw new BusinessException("The reservation has been canceled");
        } else {
            throw new BusinessException("Unknown reservation state");
        }
    }

    private static ReservationInfoResult entityToResult(ReservationEntity entity,
                                                        String username,
                                                        String teacherName,
                                                        String subjectName) {
        return ReservationInfoResult.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .username(username)
                .teacherId(entity.getTeacherId())
                .teacherName(teacherName)
                .subjectName(subjectName)
                .startTime(entity.getStartTime())
                .duration(entity.getDuration())
                .state(entity.getState())
                .build();
    }
}
