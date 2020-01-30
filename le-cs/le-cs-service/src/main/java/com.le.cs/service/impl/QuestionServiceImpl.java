package com.le.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.Question;
import com.le.cs.mapper.QuestionMapper;
import com.le.cs.service.IQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.le.core.rest.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author WXY
 * @since 2019-05-14
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

    @Override
    public R findPage(Page<Question> pagination, Question search) {
        QueryWrapper<Question> qw = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(search.getTitle())) {
            qw.like("title",search.getTitle());
        }
        IPage<Question> page = baseMapper.selectPage(pagination, qw);
        return R.success(page);
    }

    public R editData(Question question) {
        saveOrUpdate(question);
        return R.success();
    }
}
