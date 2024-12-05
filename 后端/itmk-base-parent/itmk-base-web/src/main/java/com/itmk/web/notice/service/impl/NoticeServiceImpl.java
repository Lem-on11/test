package com.itmk.web.notice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.notice.entity.Notice;
import com.itmk.web.notice.mapper.NoticeMapper;
import com.itmk.web.notice.service.NoticeService;
import org.springframework.stereotype.Service;

/**

 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
}
