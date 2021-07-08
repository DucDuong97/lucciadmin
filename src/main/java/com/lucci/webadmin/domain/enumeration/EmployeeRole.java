package com.lucci.webadmin.domain.enumeration;

import java.util.Arrays;
import java.util.List;

/**
 * The EmployeeRole enumeration.
 */
public enum EmployeeRole {
    DOCTOR {
        @Override
        public List<String> getAuthorities() {
            return Arrays.asList("ROLE_DOCTOR", "ROLE_USER");
        }
    }, NURSE {
        @Override
        public List<String> getAuthorities() {
            return Arrays.asList("ROLE_NURSE", "ROLE_USER");
        }
    }, MARKETING {
        @Override
        public List<String> getAuthorities() {
            return Arrays.asList("ROLE_MARKETING", "ROLE_USER");
        }
    }, RECEPTIONIST {
        @Override
        public List<String> getAuthorities() {
            return Arrays.asList("ROLE_RECEPTIONIST", "ROLE_USER");
        }
    }, ADMIN {
        @Override
        public List<String> getAuthorities() {
            return Arrays.asList("ROLE_ADMIN", "ROLE_USER");
        }
    },

    MANAGER {
        @Override
        public List<String> getAuthorities() {
            return Arrays.asList("ROLE_MANAGER", "ROLE_USER");
        }
    },

    OPERATIONS_DIRECTOR {
        @Override
        public List<String> getAuthorities() {
            return Arrays.asList("ROLE_OPERATIONS_DIRECTOR", "ROLE_USER");
        }
    },

    BRANCH_BOSS_DOCTOR {
        @Override
        public List<String> getAuthorities() {
            return Arrays.asList("ROLE_BRANCH_BOSS_DOCTOR", "ROLE_USER");
        }
    },

    CONSULTANT {
        @Override
        public List<String> getAuthorities() {
            return Arrays.asList("ROLE_CONSULTANT", "ROLE_USER");
        }
    };

    public abstract List<String> getAuthorities();
}
