package com.xiongus.bear.console.controller;

import com.xiongus.bear.console.service.PermissionsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Permission related methods entry.
 *
 * @author xiongus
 */
@RestController("/permission")
@AllArgsConstructor
@RequestMapping({"/api/permission", "/v1/api/permission"})
public class PermissionController {

    private final PermissionsService permissionsService;

    /**
     * Get paged permissions.
     *
     * @param pageNo number index of page
     * @param pageSize size of page
     * @return A collection of permissions, empty set if no permission is found
     */
    @GetMapping
    public Object getPermissions(@RequestParam int pageNo, @RequestParam int pageSize) {
        return permissionsService.getPermissions(pageNo, pageSize);
    }

}
