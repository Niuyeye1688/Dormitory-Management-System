package com.dormitory.service;

import com.dormitory.dao.AllocationDao;
import com.dormitory.dao.DormitoryDao;
import com.dormitory.dao.impl.AllocationDaoImpl;
import com.dormitory.dao.impl.DormitoryDaoImpl;
import com.dormitory.model.Allocation;
import com.dormitory.model.Dormitory;
import com.dormitory.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class AllocationService {

    private static final Logger logger = LoggerFactory.getLogger(AllocationService.class);
    private final AllocationDao allocationDao = new AllocationDaoImpl();
    private final DormitoryDao dormitoryDao = new DormitoryDaoImpl();

    public boolean allocate(Allocation allocation) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            // 在事务内重新查询，防止幻读
            Dormitory dormitory = dormitoryDao.findById(allocation.getDormitoryId(), conn);
            if (dormitory == null || dormitory.getStatus() != 1 || dormitory.getEmptyBeds() <= 0) {
                conn.rollback();
                return false;
            }

            Allocation existing = allocationDao.findActiveByStudentId(allocation.getStudentId(), conn);
            if (existing != null) {
                conn.rollback();
                return false;
            }

            int result = allocationDao.insert(allocation, conn);
            if (result > 0) {
                dormitoryDao.updateCurrentCount(dormitory.getId(), 1, conn);
                conn.commit();
                return true;
            }
            conn.rollback();
        } catch (SQLException e) {
            logger.error("事务操作失败", e);
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            DBUtil.close(conn, null);
        }
        return false;
    }

    public boolean checkout(Integer allocationId, String remark) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            Allocation allocation = allocationDao.findById(allocationId);
            if (allocation == null || allocation.getStatus() != 1) {
                conn.rollback();
                return false;
            }

            int result = allocationDao.checkout(allocationId, remark, conn);
            if (result > 0) {
                dormitoryDao.updateCurrentCount(allocation.getDormitoryId(), -1, conn);
                conn.commit();
                return true;
            }
            conn.rollback();
        } catch (SQLException e) {
            logger.error("事务操作失败", e);
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            DBUtil.close(conn, null);
        }
        return false;
    }

    public boolean transfer(Integer allocationId, Integer newDormitoryId, Integer bedNo, String remark) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            Allocation allocation = allocationDao.findById(allocationId);
            if (allocation == null || allocation.getStatus() != 1) {
                conn.rollback();
                return false;
            }

            Dormitory newDorm = dormitoryDao.findById(newDormitoryId, conn);
            if (newDorm == null || newDorm.getStatus() != 1 || newDorm.getEmptyBeds() <= 0) {
                conn.rollback();
                return false;
            }

            allocationDao.checkout(allocationId, "调宿: " + remark, conn);
            dormitoryDao.updateCurrentCount(allocation.getDormitoryId(), -1, conn);

            Allocation newAlloc = new Allocation();
            newAlloc.setStudentId(allocation.getStudentId());
            newAlloc.setDormitoryId(newDormitoryId);
            newAlloc.setBedNo(bedNo);
            newAlloc.setCheckInDate(new java.sql.Date(System.currentTimeMillis()));
            newAlloc.setRemark("从 " + allocation.getBuildingNo() + " " + allocation.getRoomNo() + " 调宿过来");

            int result = allocationDao.insert(newAlloc, conn);
            if (result > 0) {
                dormitoryDao.updateCurrentCount(newDormitoryId, 1, conn);
                conn.commit();
                return true;
            }
            conn.rollback();
        } catch (SQLException e) {
            logger.error("事务操作失败", e);
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            DBUtil.close(conn, null);
        }
        return false;
    }
}
