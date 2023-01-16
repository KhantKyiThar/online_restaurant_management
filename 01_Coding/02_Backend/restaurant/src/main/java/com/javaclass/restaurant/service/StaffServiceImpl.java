package com.javaclass.restaurant.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javaclass.restaurant.entity.Staff;
import com.javaclass.restaurant.repository.StaffRepo;

@Service
public class StaffServiceImpl implements StaffService {

	@Autowired
	StaffRepo staffRepo;

	@Autowired
	PasswordEncoder pwEncoder;

	@Override
	public List<Staff> getAll() {
		return staffRepo.findAll();
	}

	@Override
	public Staff get(int id) {
		return staffRepo.findById(id).orElse(null);
	}

	@Override
	public Staff create(Staff staff) {
		// Check if staff with same loginId exists
		Staff tempLoginIdCheck = staffRepo.findByLoginId(staff.getLoginId());
		if (tempLoginIdCheck != null) {
			return null;
		}
		staff.setPassword(pwEncoder.encode(staff.getPassword()));
		staff.setCreatedAt(LocalDateTime.now());
		return staffRepo.save(staff);
	}

	@Override
	public Staff update(int id, Staff staff) {

		Staff toUpdateStaff = get(id);
		// Check if staff with same loginId exists

		if (toUpdateStaff != null) {
			if (!toUpdateStaff.getLoginId().equals(staff.getLoginId())) {
				Staff tempLoginIdCheck = staffRepo.findByLoginId(staff.getLoginId());
				if (tempLoginIdCheck != null) {
					return null;
				}
			}
			toUpdateStaff.setName(staff.getName());
			toUpdateStaff.setLoginId(staff.getLoginId());
			toUpdateStaff.setPassword(pwEncoder.encode(staff.getPassword()));
			toUpdateStaff.setPhone(staff.getPhone());
			toUpdateStaff.setGender(staff.getGender());
			toUpdateStaff.setStaffType(staff.getStaffType());
			toUpdateStaff.setDeleteable(staff.isDeleteable());
			toUpdateStaff.setUpdatedAt(LocalDateTime.now());
			staffRepo.save(toUpdateStaff);
		}
		return toUpdateStaff;
	}

	@Override
	public Staff updateDeleteable(int id, Staff staff) {
		Staff toUpdateStaff = get(id);
		if (toUpdateStaff != null) {
			toUpdateStaff.setDeleteable(false);
			staffRepo.save(toUpdateStaff);
		}
		return toUpdateStaff;
	}

	@Override
	public boolean delete(int id) {
		Staff staff = get(id);
		if (staff != null) {
			staffRepo.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Staff checkLoginStaff(String loginId, String password) {
		Staff staff = staffRepo.findByLoginId(loginId);
		if (staff == null) {
			return null;
		}
		if (!pwEncoder.matches(password, staff.getPassword())) {
			return null;
		}
		return staffRepo.save(staff);
	}

	@Override
	public void updatePwd(int id, String newPwd) {
		Staff toUpdateStaffPwd = get(id);
		if (toUpdateStaffPwd != null) {
			toUpdateStaffPwd.setPassword(pwEncoder.encode(newPwd));
			staffRepo.save(toUpdateStaffPwd);
			System.out.println("pwd updated");
		}
	}

}
