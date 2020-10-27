package com.daizhiyuan.dms.service;

import com.daizhiyuan.dms.entity.Dorm;
import com.baomidou.mybatisplus.extension.service.IService;
import com.daizhiyuan.dms.response.Response;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhu
 * @since 2020-10-19
 */
public interface DormService extends IService<Dorm> {

    Response getListDorm(int page,String buildingNum);

    Response getBuildingTag();

    Response insertDorm(Dorm dorm);

    Response getDormOption();

}
