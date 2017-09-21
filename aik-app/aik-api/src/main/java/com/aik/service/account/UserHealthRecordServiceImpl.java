package com.aik.service.account;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.AikHealthRecordMapper;
import com.aik.dao.AikHrBloodSugarMapper;
import com.aik.dao.AikHrRoutineBloodMapper;
import com.aik.dao.AikHrTumorMarkersMapper;
import com.aik.dto.AddHealthRecordDTO;
import com.aik.enums.ExcursionEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikHealthRecord;
import com.aik.model.AikHrBloodSugar;
import com.aik.model.AikHrRoutineBlood;
import com.aik.model.AikHrTumorMarkers;
import com.aik.resource.SystemResource;
import com.aik.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> getLastHealthRecordDetail(Integer userId) throws ApiServiceException {
        if (null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        Map<String, Object> rsData = new HashMap<>();
        AikHealthRecord aikHealthRecord = aikHealthRecordMapper.selectLastRecordByUserId(userId);
        if (null == aikHealthRecord) {
            return rsData;
        }

        rsData.put("height", aikHealthRecord.getHeight());
        rsData.put("weight", aikHealthRecord.getWeight());
        rsData.put("medicalRecord", systemResource.getApiFileUri() + aikHealthRecord.getMedicalRecord());

        // 血糖类
        AikHrBloodSugar aikHrBloodSugar = aikHrBloodSugarMapper.selectByHRid(aikHealthRecord.getId());
        if (null != aikHrBloodSugar) {
            rsData.put("aikHrBloodSugar", compareHrBS(aikHrBloodSugar));
        }

        // 血常规指标类
        AikHrRoutineBlood aikHrRoutineBlood = aikHrRoutineBloodMapper.selectByHRid(aikHealthRecord.getId());
        if (null != aikHrRoutineBlood) {
            rsData.put("aikHrRoutineBlood", compareHrRB(aikHrRoutineBlood));
        }

        // 肿瘤标志物指标
        AikHrTumorMarkers aikHrTumorMarkers = aikHrTumorMarkersMapper.selectByHRid(aikHealthRecord.getId());
        if (null != aikHrTumorMarkers) {
            rsData.put("aikHrTumorMarkers", compareHrTM(aikHrTumorMarkers));
        }

        return rsData;
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
    public Map<String, Object> getHealthRecordDetail(Integer healthRecordId) throws ApiServiceException {
        if (null == healthRecordId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        Map<String, Object> healthRecordDetail = new HashMap<>();
        AikHealthRecord aikHealthRecord = aikHealthRecordMapper.selectByPrimaryKey(healthRecordId);
        if (null == aikHealthRecord) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004005);
        }

        healthRecordDetail.put("height", aikHealthRecord.getHeight());
        healthRecordDetail.put("weight", aikHealthRecord.getWeight());
        healthRecordDetail.put("medicalRecord", systemResource.getApiFileUri() + aikHealthRecord.getMedicalRecord());

        // 血糖类
        AikHrBloodSugar aikHrBloodSugar = aikHrBloodSugarMapper.selectByHRid(aikHealthRecord.getId());
        if (null != aikHrBloodSugar) {
            healthRecordDetail.put("aikHrBloodSugar", compareHrBS(aikHrBloodSugar));
        }

        // 血常规指标类
        AikHrRoutineBlood aikHrRoutineBlood = aikHrRoutineBloodMapper.selectByHRid(aikHealthRecord.getId());
        if (null != aikHrRoutineBlood) {
            healthRecordDetail.put("aikHrRoutineBlood", compareHrRB(aikHrRoutineBlood));
        }

        // 肿瘤标志物指标
        AikHrTumorMarkers aikHrTumorMarkers = aikHrTumorMarkersMapper.selectByHRid(aikHealthRecord.getId());
        if (null != aikHrTumorMarkers) {
            healthRecordDetail.put("aikHrTumorMarkers", compareHrTM(aikHrTumorMarkers));
        }

        return healthRecordDetail;
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
    private Map<String, Object> compareHrBS(AikHrBloodSugar aikHrBloodSugar) {
        Map<String, Object> map = new HashMap<>();
        // bloodSugar, tch, tg, hdlC, ldlC, cr, bun
        map.put("bloodSugar", aikHrBloodSugar.getBloodSugar());
        map.put("tch", aikHrBloodSugar.getTch());
        map.put("tg", aikHrBloodSugar.getTg());
        map.put("hdlC", aikHrBloodSugar.getHdlC());
        map.put("ldlC", aikHrBloodSugar.getLdlC());
        map.put("cr", aikHrBloodSugar.getCr());
        map.put("bun", aikHrBloodSugar.getBun());

        map.put("bloodSugarRemark", ExcursionEnum.NORMAL.getCode());
        map.put("tchRemark", ExcursionEnum.NORMAL.getCode());
        map.put("tgRemark", ExcursionEnum.NORMAL.getCode());
        map.put("hdlCRemark", ExcursionEnum.NORMAL.getCode());
        map.put("ldlCRemark", ExcursionEnum.NORMAL.getCode());
        map.put("crRemark", ExcursionEnum.NORMAL.getCode());
        map.put("bunRemark", ExcursionEnum.NORMAL.getCode());

        return map;
    }

    /**
     * TODO:对比基础设定值
     *
     * @param aikHrRoutineBlood 血常规指标类
     * @return map
     */
    private Map<String, Object> compareHrRB(AikHrRoutineBlood aikHrRoutineBlood) {
        Map<String, Object> map = new HashMap<>();
        // hb, rbc, wbc, plt, ret, bn, sn, eos, baso, lym, mnc
        map.put("hb", aikHrRoutineBlood.getHb());
        map.put("rbc", aikHrRoutineBlood.getRbc());
        map.put("wbc", aikHrRoutineBlood.getWbc());
        map.put("plt", aikHrRoutineBlood.getPlt());
        map.put("ret", aikHrRoutineBlood.getRet());
        map.put("bn", aikHrRoutineBlood.getBn());
        map.put("sn", aikHrRoutineBlood.getSn());
        map.put("eos", aikHrRoutineBlood.getEos());
        map.put("baso", aikHrRoutineBlood.getBaso());
        map.put("lym", aikHrRoutineBlood.getLym());
        map.put("mnc", aikHrRoutineBlood.getMnc());

        map.put("hbRemark", ExcursionEnum.NORMAL.getCode());
        map.put("rbcRemark", ExcursionEnum.NORMAL.getCode());
        map.put("wbcRemark", ExcursionEnum.NORMAL.getCode());
        map.put("pltRemark", ExcursionEnum.NORMAL.getCode());
        map.put("retRemark", ExcursionEnum.NORMAL.getCode());
        map.put("bnRemark", ExcursionEnum.NORMAL.getCode());
        map.put("snRemark", ExcursionEnum.NORMAL.getCode());
        map.put("eosRemark", ExcursionEnum.NORMAL.getCode());
        map.put("basoRemark", ExcursionEnum.NORMAL.getCode());
        map.put("lymRemark", ExcursionEnum.NORMAL.getCode());
        map.put("mncRemark", ExcursionEnum.NORMAL.getCode());

        return map;
    }

    /**
     * TODO:对比基础设定值
     *
     * @param aikHrTumorMarkers 肿瘤标志物指标
     * @return map
     */
    private Map<String, Object> compareHrTM(AikHrTumorMarkers aikHrTumorMarkers) {
        Map<String, Object> map = new HashMap<>();
        // afp, cea, ca19, pg, ca72, scc
        map.put("afp", aikHrTumorMarkers.getAfp());
        map.put("cea", aikHrTumorMarkers.getCea());
        map.put("ca19", aikHrTumorMarkers.getCa19());
        map.put("pg", aikHrTumorMarkers.getPg());
        map.put("ca72", aikHrTumorMarkers.getCa72());
        map.put("scc", aikHrTumorMarkers.getScc());

        map.put("afpRemark", ExcursionEnum.NORMAL.getCode());
        map.put("ceaRemark", ExcursionEnum.NORMAL.getCode());
        map.put("ca19Remark", ExcursionEnum.NORMAL.getCode());
        map.put("pgRemark", ExcursionEnum.NORMAL.getCode());
        map.put("ca72Remark", ExcursionEnum.NORMAL.getCode());
        map.put("sccRemark", ExcursionEnum.NORMAL.getCode());

        return map;
    }
}
