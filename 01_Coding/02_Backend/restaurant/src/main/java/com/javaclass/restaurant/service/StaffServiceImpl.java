package com.javaclass.restaurant.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaclass.restaurant.entity.Staff;
import com.javaclass.restaurant.repository.StaffRepo;

@Service
public class StaffServiceImpl implements StaffService {

	@Autowired
	StaffRepo staffRepo;

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
		staff.setCreatedAt(LocalDateTime.now());
		return staffRepo.save(staff);
	}

	@Override
	public Staff update(int id, Staff staff) {
		Staff toUpdateStaff = get(id);
		if (toUpdateStaff != null) {
			toUpdateStaff.setName(staff.getName());
			toUpdateStaff.setLoginId(staff.getLoginId());
			toUpdateStaff.setPassword(staff.getPassword());
			toUpdateStaff.setPhone(staff.getPhone());
			toUpdateStaff.setGender(staff.getGender());
			toUpdateStaff.setStaffType(staff.getStaffType());
			toUpdateStaff.setUpdatedAt(LocalDateTime.now());
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

}
