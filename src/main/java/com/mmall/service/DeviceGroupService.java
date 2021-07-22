package com.mmall.service;

import com.google.common.base.Preconditions;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.dao.CameraMapper;
import com.mmall.dao.Device_GroupMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.Camera;
import com.mmall.model.Device_Group;
import com.mmall.param.CameraParam;
import com.mmall.param.DeviceGroupParam;
import com.mmall.util.BeanValidator;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;
@Service
public class DeviceGroupService {
    @Resource
    private Device_GroupMapper device_groupMapper;
    @Resource
    SqlSessionTemplate sqlSessionTemplate;

    public void save(DeviceGroupParam param) throws ParseException {

        BeanValidator.check(param);
        if (checkExistByTargetId(param.getTarget_id(),param.getId())) {
            throw new ParamException("该靶机设备编号已经存在");
        }
        if (checkExistByCameraId(param.getCamera_id(),param.getId())) {
            throw new ParamException("该照靶终端编号已经存在");
        }
        if (checkExistByDisplayId(param.getDisplay_id(),param.getId())) {
            throw new ParamException("该显靶终端编号已经存在");
        }
        if (checkExistByDeviceGroupIndex(param.getGroup_index(),param.getId())) {
            throw new ParamException("该靶位编号已经存在");
        }
        Device_Group device_group = Device_Group.builder().groupIndex(param.getGroup_index()).targetId(param.getTarget_id())
                .cameraId(param.getCamera_id()).displayId(param.getDisplay_id()).status(param.getStatus()).memo(param.getMemo()).build();
        device_groupMapper.insertSelective(device_group);
    }
    public PageResult<Device_Group> getPage(PageQuery page) {
        BeanValidator.check(page);
        int count = device_groupMapper.count();
        if (count > 0) {
            List<Device_Group> list = device_groupMapper.getPage(page);
            return PageResult.<Device_Group>builder().total(count).data(list).build();
        }
        return PageResult.<Device_Group>builder().build();
    }
    public Device_Group getDeviceGroupByIndex(Integer group_index) {
    Device_Group device_group=device_groupMapper.getDeviceGroupByIndex(group_index);
    return device_group;
    }


    public Device_Group getDeviceGroupByCameraIndex(Integer camera_index) {
        Device_Group device_group=device_groupMapper.getDeviceGroupByCameraIndex(camera_index);
        return device_group;
    }
    public void delete(int deviceGroupId) {
        Device_Group device_group = device_groupMapper.selectByPrimaryKey(deviceGroupId);
        Preconditions.checkNotNull(device_group, "待删除的训练计划不存在，无法删除");
        //TODO 删除时判断有没有依赖数据,比如打靶成绩
        device_groupMapper.deleteByPrimaryKey(deviceGroupId);
    }
    public void update(DeviceGroupParam param) throws ParseException {
        BeanValidator.check(param);
        if (checkExistByTargetId(param.getTarget_id(),param.getId())) {
            throw new ParamException("该靶机设备编号已经存在");
        }
        if (checkExistByCameraId(param.getCamera_id(),param.getId())) {
            throw new ParamException("该照靶终端编号已经存在");
        }
        if (checkExistByDisplayId(param.getDisplay_id(),param.getId())) {
            throw new ParamException("该显靶终端编号已经存在");
        }
        if (checkExistByDeviceGroupIndex(param.getGroup_index(),param.getId())) {
            throw new ParamException("该靶位编号已经存在");
        }
        Device_Group before = device_groupMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的设备不存在");

        Device_Group after = Device_Group.builder().id(param.getId()).groupIndex(param.getGroup_index()).targetId(param.getTarget_id()).cameraId(param.getCamera_id())
                .displayId(param.getDisplay_id()).status(param.getStatus()).memo(param.getMemo()).build();
        device_groupMapper.updateByPrimaryKeySelective(after);

    }
    public PageResult<Device_Group> getAll() {
        int count = device_groupMapper.count();
        if (count > 0) {
            List<Device_Group> list = device_groupMapper.getAll();
            return PageResult.<Device_Group>builder().total(count).data(list).build();
        }
        return PageResult.<Device_Group>builder().build();
    }

    public int getCount(){
        int count = device_groupMapper.count();
        return count;
    }

    private boolean checkExistByTargetId(Integer targetId,Integer id) {
        return device_groupMapper.countByTargetId(targetId,id) > 0;
    }
    private boolean checkExistByCameraId(Integer cameraId,Integer id) {
        return device_groupMapper.countByCameraId(cameraId,id) > 0;
    }
    private boolean checkExistByDisplayId(Integer displayId,Integer id) {//所处靶位编号
        return device_groupMapper.countDisplayId(displayId,id) > 0;
    }
    private boolean checkExistByDeviceGroupIndex(Integer group_index,Integer id) {//设备编号
        return device_groupMapper.countByGroupIndex(group_index,id) > 0;
    }
}
