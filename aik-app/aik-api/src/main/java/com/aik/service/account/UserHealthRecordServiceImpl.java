package com.aik.service.account;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.AikHealthRecordMapper;
import com.aik.dao.AikHrBloodSugarMapper;
import com.aik.dao.AikHrRoutineBloodMapper;
import com.aik.dao.AikHrTumorMarkersMapper;
import com.aik.dto.AddHealthRecordDTO;
import com.aik.dto.response.HealthRecordRespDTO;
import com.aik.enums.ExcursionEnum;
import com.aik.enums.HrBloodSugarElemEnum;
import com.aik.enums.HrRoutineBloodElemEnum;
import com.aik.enums.HrTumorMarkersElemEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikHealthRecord;
import com.aik.model.AikHrBloodSugar;
import com.aik.model.AikHrRoutineBlood;
import com.aik.model.AikHrTumorMarkers;
import com.aik.resource.SystemResource;
import com.aik.util.DateUtils;
import com.aik.vo.HealthRecordElementVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * Description:
 * Created by as on 2017/8/13.
 */
@Service
public class UserHealthRecordServiceImpl implements UserHealthRecordService {

    private static final Logger logger = LoggerFactory.getLogger(UserHealthRecordServiceImpl.class);

    private AikHealthRecordMapper aikHealthRecordMapper;

    private AikHrBloodSugarMapper aikHrBloodSugarMapper;

    private AikHrRoutineBloodMapper aikHrRoutineBloodMapper;

    private AikHrTumorMarkersMapper aikHrTumorMarkersMapper;

    @Resource
    private SystemResource systemResource;

    @Autowired
    public void setAikHealthRecordMapper(AikHealthRecordMapper aikHealthRecordMapper) {
        this.aikHealthRecordMapper = aikHealthRecordMapper;
    }

    @Autowired
    public void setAikHrBloodSugarMapper(AikHrBloodSugarMapper aikHrBloodSugarMapper) {
        this.aikHrBloodSugarMapper = aikHrBloodSugarMapper;
    }

    @Autowired
    public void setAikHrRoutineBloodMapper(AikHrRoutineBloodMapper aikHrRoutineBloodMapper) {
        this.aikHrRoutineBloodMapper = aikHrRoutineBloodMapper;
    }

    @Autowired
    public void setAikHrTumorMarkersMapper(AikHrTumorMarkersMapper aikHrTumorMarkersMapper) {
        this.aikHrTumorMarkersMapper = aikHrTumorMarkersMapper;
    }

    @Override
    public HealthRecordRespDTO getLastHealthRecordDetail(Integer userId) throws ApiServiceException {
        if (null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikHealthRecord aikHealthRecord = aikHealthRecordMapper.selectLastRecordByUserId(userId);
        if (null == aikHealthRecord) {
            return null;
        }

        return convertHealthRecordDetail(aikHealthRecord);
    }

    @Override
    public List<Map<String, Object>> getUserHealthRecords(Map<String, Object> params) throws ApiServiceException {
        List<Map<String, Object>> healthRecords = aikHealthRecordMapper.selectByParams(params);

        for (Map<String, Object> healthRecord : healthRecords) {
            healthRecord.put("createDate", DateUtils.showDate((Date) healthRecord.get("createDate")));
        }

        return healthRecords;
    }

    @Override
    public HealthRecordRespDTO getHealthRecordDetail(Integer healthRecordId) throws ApiServiceException {
        if (null == healthRecordId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikHealthRecord aikHealthRecord = aikHealthRecordMapper.selectByPrimaryKey(healthRecordId);
        if (null == aikHealthRecord) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004005);
        }

        return convertHealthRecordDetail(aikHealthRecord);
    }

    private HealthRecordRespDTO convertHealthRecordDetail(AikHealthRecord aikHealthRecord) {
        HealthRecordRespDTO healthRecord = new HealthRecordRespDTO();
        if (null == aikHealthRecord) {
            return healthRecord;
        }

        healthRecord.setHeight(aikHealthRecord.getHeight());
        healthRecord.setWeight(aikHealthRecord.getWeight());
        healthRecord.setMedicalRecord(systemResource.getApiFileUri() + aikHealthRecord.getMedicalRecord());

        // 血糖类
        AikHrBloodSugar aikHrBloodSugar = aikHrBloodSugarMapper.selectByHRid(aikHealthRecord.getId());
        if (null != aikHrBloodSugar) {
            healthRecord.setHrBloodSugar(compareHrBS(aikHrBloodSugar));
        }

        // 血常规指标类
        AikHrRoutineBlood aikHrRoutineBlood = aikHrRoutineBloodMapper.selectByHRid(aikHealthRecord.getId());
        if (null != aikHrRoutineBlood) {
            healthRecord.setHrRoutineBlood(compareHrRB(aikHrRoutineBlood));
        }

        // 肿瘤标志物指标
        AikHrTumorMarkers aikHrTumorMarkers = aikHrTumorMarkersMapper.selectByHRid(aikHealthRecord.getId());
        if (null != aikHrTumorMarkers) {
            healthRecord.setHrTumorMarkers(compareHrTM(aikHrTumorMarkers));
        }

        return healthRecord;
    }

    @Override
    public void addHealthRecord(AddHealthRecordDTO addHealthRecordDTO, Integer userId) throws ApiServiceException {
        if (null == addHealthRecordDTO || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikHealthRecord aikHealthRecord = addHealthRecordDTO.getAikHealthRecord();
        aikHealthRecord.setUserId(userId);
        aikHealthRecord.setCreateDate(new Date());
        aikHealthRecordMapper.insertSelective(aikHealthRecord);

        AikHrBloodSugar aikHrBloodSugar = addHealthRecordDTO.getAikHrBloodSugar();
        aikHrBloodSugar.setUserId(userId);
        aikHrBloodSugar.setHrId(aikHealthRecord.getId());
        aikHrBloodSugar.setCreateDate(new Date());
        aikHrBloodSugarMapper.insertSelective(aikHrBloodSugar);

        AikHrRoutineBlood aikHrRoutineBlood = addHealthRecordDTO.getAikHrRoutineBlood();
        aikHrRoutineBlood.setUserId(userId);
        aikHrRoutineBlood.setHrId(aikHealthRecord.getId());
        aikHrRoutineBlood.setCreateDate(new Date());
        aikHrRoutineBloodMapper.insertSelective(aikHrRoutineBlood);

        AikHrTumorMarkers aikHrTumorMarkers = addHealthRecordDTO.getAikHrTumorMarkers();
        aikHrTumorMarkers.setUserId(userId);
        aikHrTumorMarkers.setHrId(aikHealthRecord.getId());
        aikHrTumorMarkers.setCreateDate(new Date());
        aikHrTumorMarkersMapper.insertSelective(aikHrTumorMarkers);
    }

    /**
     * TODO:对比基础设定值
     *
     * @param aikHrBloodSugar 血糖类
     * @return map
     */
    private List<HealthRecordElementVO> compareHrBS(AikHrBloodSugar aikHrBloodSugar) {
        List<HealthRecordElementVO> hrElements = new ArrayList<>();
        if (null == aikHrBloodSugar) {
            return hrElements;
        }

        try {
            Class clazz = AikHrBloodSugar.class;
            for (HrBloodSugarElemEnum elemEnum : HrBloodSugarElemEnum.values()) {
                Field field = clazz.getDeclaredField(elemEnum.getField());
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method method = pd.getReadMethod();
                BigDecimal content = (BigDecimal) method.invoke(aikHrBloodSugar);

                HealthRecordElementVO elem = new HealthRecordElementVO();
                elem.setElemName(elemEnum.getElemName());
                elem.setElemContent(content);
                elem.setElemRemark(ExcursionEnum.NORMAL.getCode());
                elem.setElemUnit(elemEnum.getUnit());
            }
        } catch (Exception e) {
            // ignore
        }

        return hrElements;
    }

    /**
     * TODO:对比基础设定值
     *
     * @param aikHrRoutineBlood 血常规指标类
     * @return map
     */
    private List<HealthRecordElementVO> compareHrRB(AikHrRoutineBlood aikHrRoutineBlood) {
        List<HealthRecordElementVO> hrElements = new ArrayList<>();
        if (null == aikHrRoutineBlood) {
            return hrElements;
        }

        try {
            Class clazz = AikHrRoutineBlood.class;
            for (HrRoutineBloodElemEnum elemEnum : HrRoutineBloodElemEnum.values()) {
                Field field = clazz.getDeclaredField(elemEnum.getField());
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method method = pd.getReadMethod();
                BigDecimal content = (BigDecimal) method.invoke(aikHrRoutineBlood);

                HealthRecordElementVO elem = new HealthRecordElementVO();
                elem.setElemName(elemEnum.getElemName());
                elem.setElemContent(content);
                elem.setElemRemark(ExcursionEnum.NORMAL.getCode());
                elem.setElemUnit(elemEnum.getUnit());
            }
        } catch (Exception e) {
            // ignore
        }

        return hrElements;
    }

    /**
     * TODO:对比基础设定值
     *
     * @param aikHrTumorMarkers 肿瘤标志物指标
     * @return map
     */
    private List<HealthRecordElementVO> compareHrTM(AikHrTumorMarkers aikHrTumorMarkers) {
        List<HealthRecordElementVO> hrElements = new ArrayList<>();
        if (null == aikHrTumorMarkers) {
            return hrElements;
        }

        try {
            Class clazz = AikHrTumorMarkers.class;
            for (HrTumorMarkersElemEnum elemEnum : HrTumorMarkersElemEnum.values()) {
                Field field = clazz.getDeclaredField(elemEnum.getField());
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method method = pd.getReadMethod();
                BigDecimal content = (BigDecimal) method.invoke(aikHrTumorMarkers);

                HealthRecordElementVO elem = new HealthRecordElementVO();
                elem.setElemName(elemEnum.getElemName());
                elem.setElemContent(content);
                elem.setElemRemark(ExcursionEnum.NORMAL.getCode());
                elem.setElemUnit(elemEnum.getUnit());
            }
        } catch (Exception e) {
            // ignore
        }

        return hrElements;
    }
}
