package tw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.dao.PornRecordDao;

@Service
public class PornRecordService {

    @Autowired
    private PornRecordDao pornRecordDao;



}