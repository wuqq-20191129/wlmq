/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.Store;
import java.util.List;

/**
 *
 * @author mh
 */
public interface StoreMapper {

    public List<Store> getStore(Store store);

    public int addStore(Store store);

    public int modifyStore(Store store);

    public List<Store> getStoreById(Store store);
    
    public List<Store> getStoreByName(Store store);

    public int deleteStore(Store store);

}
