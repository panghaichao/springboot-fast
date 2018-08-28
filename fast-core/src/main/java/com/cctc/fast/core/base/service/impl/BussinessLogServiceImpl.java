package com.cctc.fast.core.base.service.impl;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cctc.fast.core.base.entity.OperateLog;
import com.cctc.fast.core.base.mapper.BussinessLogMapper;
import com.cctc.fast.core.base.service.IBussinessLogService;
@Service
public class BussinessLogServiceImpl extends ServiceImpl<BussinessLogMapper, OperateLog> implements IBussinessLogService {


}
