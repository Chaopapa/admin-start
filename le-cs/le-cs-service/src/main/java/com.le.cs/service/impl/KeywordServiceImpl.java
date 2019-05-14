package com.le.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.Keyword;
import com.le.cs.mapper.KeywordMapper;
import com.le.cs.service.IKeywordService;
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
public class KeywordServiceImpl extends ServiceImpl<KeywordMapper, Keyword> implements IKeywordService {

    @Override
    public R findPage(Page<Keyword> pagination, Keyword search) {
        QueryWrapper<Keyword> qw = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(search.getKeyword())) {
            qw.like("keyword",search.getKeyword());
        }
        IPage<Keyword> page = baseMapper.selectPage(pagination, qw);
        return R.success(page);
    }

    public R editData(Keyword keyword) {
        saveOrUpdate(keyword);
        return R.success();
    }
}
