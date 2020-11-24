package com.goldsign.acc.app.opma.mapper;

import com.goldsign.acc.app.opma.entity.CodPubFlag;
import java.util.List;
import java.util.Map;



public interface CodPubFlagMapper  {
    
    public List<CodPubFlag> find(Map<String,Object> map);
    
}