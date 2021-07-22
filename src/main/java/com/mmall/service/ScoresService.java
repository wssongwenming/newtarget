package com.mmall.service;

import com.mmall.dao.DisplayMapper;
import com.mmall.dao.ScoresMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.Scores;
import com.mmall.model.Training;
import com.mmall.param.ScoresParam;
import com.mmall.param.TrainingParam;
import com.mmall.util.BeanValidator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

@Service
public class ScoresService {
    @Resource
    private ScoresMapper scoresMapper;
    public List<Scores> getScoresByTraineeId(Integer traineeId){
        List<Scores> scoresList=scoresMapper.getByTraineeId(traineeId);
        return scoresList;
    }

    public int getCount(Integer traineeId){
        int count = scoresMapper.count(traineeId);
        return count;
    }

    public void save(ScoresParam param) throws ParseException {

        BeanValidator.check(param);
        Scores scores = Scores.builder().hittingTime(param.getHittingTime()).mac(param.getMac()).mmofradius(param.getMmofradius())
                .mx(param.getMx()).my(param.getMy()).offset(param.getOffset()).px(param.getPx()).py(param.getPy()).lx(param.getLx()).ly(param.getLy()).rx(param.getRx()).ry(param.getRy())
//                .radius(param.getRadius()).ringnumber(param.getRingnumber()).scoreIndex(param.getScoreIndex()).traineeId(param.getTraineeId())
                .radius(param.getRadius()).ringnumber((int)(Double.parseDouble(param.getRingnumber()+""))).scoreIndex(param.getScoreIndex()).traineeId(param.getTraineeId())
                .image(param.getImagebase64()).traineeId(param.getTraineeId()).build();
        scoresMapper.insertSelective(scores);
    }

    public int deleteByTraineeId(Integer traineeId){
        return scoresMapper.deleteByTraineeId(traineeId);
    }

    public BigDecimal getScoresSumByTraineeId(Integer traineeId){
        return scoresMapper.getScoresSumByTraineeId(traineeId);
    }
}
