package com.sivitsky.service;

import com.sivitsky.domain.ListRole;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public List<String> getRoles() {
        List<String> listRoles = new ArrayList<String>();
        listRoles.add(ListRole.ROLE_ADMIN.toString());
        listRoles.add(ListRole.ROLE_USER.toString());
        return listRoles;
    }
}
