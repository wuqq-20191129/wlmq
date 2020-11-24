/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.system.mapper;

import com.goldsign.acc.app.system.entity.Date;

/**
 *
 * @author luck
 */
public interface DateMapper {

    public int select(Date date);

    public int update(Date vo);

    public int insertIntoCur(Date vo);
    
}
