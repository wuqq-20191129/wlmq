package com.goldsign.acc.frame.mapper;

import java.util.List;
import java.util.Map;

public interface MonitorStatuMapper {
    public List<Map> getMenuStatus();

    public void updateMenuStatus(Map map);
}
